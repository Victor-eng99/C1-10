package dominio;

import presentacion.ColectorParametros; 
import presentacion.GeneradorFrame;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/* Nombre: GeneradorLaberinto
 * Tipo: Clase
 * Funcion: Clase principal encargada de construir el laberinto mediante el algoritmo de Wilson
 */
public class GeneradorLaberinto {
	Celda[][] laberinto; // Variable que representa el array contenedor del laberinto
	ColectorParametros colectorParametros;
	GeneradorFrame generadorFrame;
	GeneradorJSON generadorJSON;
	int[] direccion = {0, 1, 2, 3}; // Vector que representa cada direccion. 0 = N, 1 = E, 2 = S, 3 = O
	private static final int TEMPORALIDAD = 60;

	public GeneradorLaberinto() {
		// Vacio
	}
	
	/* Nombre: run
	 * Tipo: Metodo
	 * Funcion: Ejecutar construcción de JFrame segun parametros
	 * introducidos por teclado
	 */
	public void run() throws IOException {
		colectorParametros = new ColectorParametros();
		generadorFrame = new GeneradorFrame();
		generadorJSON = new GeneradorJSON();
		int[] parametros; // Array que recogera anchura y altura del laberinto
		ArrayList<int[]> movimientos = new ArrayList<int[]>();
		ArrayList<String> idMov = new ArrayList<String>();
		
		for(int auxMov=0; auxMov<direccion.length; auxMov++) {
			int[] movimiento = new int[2];
			if(direccion[auxMov]==0) {
				movimiento[0] = -1;
				movimiento[1] = 0;
    		}
    		else if(direccion[auxMov]==1) {
    			movimiento[0] = 0;
				movimiento[1] = 1;
    		}
    		else if(direccion[auxMov]==2) {
    			movimiento[0] = 1;
				movimiento[1] = 0;
    		}
    		else {
    			movimiento[0] = 0;
				movimiento[1] = -1;
    		}
			movimientos.add(movimiento);
		}
		
		for(int auxIdMov=0; auxIdMov<direccion.length; auxIdMov++) {
			if(direccion[auxIdMov]==0) {
				idMov.add("N");
    		}
    		else if(direccion[auxIdMov]==1) {
    			idMov.add("E");
    		}
    		else if(direccion[auxIdMov]==2) {
    			idMov.add("S");
    		}
    		else {
    			idMov.add("O");
    		}
		}
		
		parametros = colectorParametros.pedirParametros();
		
		laberinto = new Celda[parametros[0]][parametros[1]];
		for (int i = 0; i < laberinto.length; i++) {
			for (int j = 0; j < laberinto[i].length; j++) {
				laberinto[i][j] = new Celda(j,i); // j = posicion X en el dibujo e i = posicion Y en el dibujo
			}
		}
		
		generadorFrame.generar(laberinto);
		
		comenzar(laberinto);
		
		generadorJSON.generar(laberinto, parametros, movimientos, idMov);
		
	}
	
	/* Nombre: comenzar
	 * Tipo: Metodo
	 * Funcion: Busqueda de primera celda aleatoria, seleccion de siguiente celda aleatoria y llamada a procedimiento
	 * de busqueda
	 */
	private void comenzar(Celda[][] laberinto) {
		Random rand = new Random();
		Celda siguiente;
		int contador=0; // Variable que representa un contador ayuda para corroborar el color de las celdas del laberinto
		
		laberinto[rand.nextInt(laberinto.length)][rand.nextInt(laberinto[0].length)].setColor(Color.WHITE);
		laberinto[rand.nextInt(laberinto.length)][rand.nextInt(laberinto[0].length)].setBlanca(true);

		do {
			siguiente = laberinto[rand.nextInt(laberinto.length)][rand.nextInt(laberinto[0].length)];
		} while(siguiente.isBlanca()); // Evitamos seleccionar de nuevo la primera celda
		
		while(siguiente != null) {
			contador = 0;
			posibleCamino(siguiente.getFila(), siguiente.getColumna(), laberinto);
			for(int i=0; i < laberinto.length; i++) { // Si encontramos una celda color blanco, aumentamos el contador en 1 unidad 
				for(int j=0; j < laberinto[0].length; j++) {
					if(laberinto[i][j].getColor().equals(Color.WHITE)) {
						contador++;
					}
				}
			}
			if(contador == laberinto.length*laberinto[0].length) { // Si el número de celdas blancas coincide con el número de celdas total, fin
				break;
			} else {
				siguiente = elegirSiguienteCelda();
			}
			
		}
		
	}
	
	/* Nombre posibleCamino
	 * Tipo: Metodo
	 * Funcion: Metodo para construir caminos desde una celda aleatoria hasta cualquier celda color blanca
	 */
	private void posibleCamino(int f, int c, Celda[][] laberinto) {
		Random rand = new Random();
		boolean blanco=false;
		ArrayList<Celda> camino = new ArrayList<Celda>(); // ArrayList que almacenara celdas del posible camino
		ArrayList<Integer> movimientosPrevios = new ArrayList<Integer>();
		
		camino.add(laberinto[f][c]);

		laberinto[f][c].setColor(Color.RED); // Primeramente, el posible camino se mostrara en rojo
		
		movimientosPrevios.add(-1); // Para primera comprobacion de movimientos previos
		laberinto[f][c].setDirHastaCelda(-1);

		do {
			ArrayList<Integer> candidatas = new ArrayList<Integer>(); // ArrayList (renovado en cada iteracion) que almacena los numeros representativos de direcciones candidatas
		
			int prev = movimientosPrevios.get(movimientosPrevios.size()-1);
			if (f-1 >= 0 && prev != 2) candidatas.add(0); // Si queremos ir al N, debemos estar en el rango de filas y haber hecho cualquier movimiento menos al S
			if (c+1 <= laberinto[0].length-1 && prev != 3) candidatas.add(1); // Si queremos ir al E, debemos estar en el rango de columnas y haber hecho cualquier movimiento menos al O
			if (f+1 <= laberinto.length-1 && prev != 0) candidatas.add(2); // Si queremos ir al S, debemos estar en el rango de filas y haber hecho cualquier movimiento menos al N
			if (c-1 >= 0 && prev != 1) candidatas.add(3); // Si queremos ir al O, debemos estar en el rango de columnas y haber hecho cualquier movimiento menos al E
			
			int mover = candidatas.get(rand.nextInt(candidatas.size())); // Seleccionamos uno de los movimientos posibles al azar
			
			if (mover == 0) f--;
			if (mover == 1) c++;
			if (mover == 2) f++;
			if (mover == 3) c--;
			
			camino.add(laberinto[f][c]); // Añadimos la siguiente celda vecina aleatoria al camino
			laberinto[f][c].setDirHastaCelda(mover);
			
			if(laberinto[f][c].getColor().equals(Color.WHITE)) {
				for (Celda celda : camino) {
					celda.setColor(Color.WHITE);
					celda.setBlanca(true);
					if(celda.getDirHastaCelda()>=0) {
						if(celda.getDirHastaCelda()==0) { // El atributo que indica la direccion que hemos tomado para escoger esa celda nos ayuda a quitar los muros correspondientes
							laberinto[celda.getFila()][celda.getColumna()].setMuro(2, true);
							laberinto[celda.getFila()+1][celda.getColumna()].setMuro(0, true);
						}
						if(celda.getDirHastaCelda()==1) {
							laberinto[celda.getFila()][celda.getColumna()].setMuro(3, true);
							laberinto[celda.getFila()][celda.getColumna()-1].setMuro(1, true);
						}
						if(celda.getDirHastaCelda()==2) {
							laberinto[celda.getFila()][celda.getColumna()].setMuro(0, true);
							laberinto[celda.getFila()-1][celda.getColumna()].setMuro(2, true);
						}
						if(celda.getDirHastaCelda()==3) {
							laberinto[celda.getFila()][celda.getColumna()].setMuro(1, true);
							laberinto[celda.getFila()][celda.getColumna()+1].setMuro(3, true);
						}
					}
				}
				
				blanco=true;
				generadorFrame.repintar();

			} else if(laberinto[f][c].getColor().equals(Color.RED)) { // Si hemos añadido al camino una celda color ROJO...
				
				int indiceRetorno = 0; // Variable que representa el punto de partida tras haber resuelto el bucle en cuestion
				
				for (int i=0; i < camino.size(); i++) { // La celda se debe encontrar consigo misma en un indice menor del ArrayList
					if(camino.get(i).getFila()==laberinto[f][c].getFila() && camino.get(i).getColumna()==laberinto[f][c].getColumna()) {
						indiceRetorno = i;
						break;
					}
				}
				
				for(int j=indiceRetorno+1; j<camino.size()-1; j++) { // Repintamos de gris todo menos la celda que hemos seleccionado repetida y las anteriores a ella
					camino.get(j).setColor(Color.GRAY);
				}
				
				for(int aux1=camino.size()-1; aux1>indiceRetorno; aux1--) { // Limpia el ArrayList camino
					camino.get(aux1).setDirHastaCelda(-1);
					camino.remove(aux1);
				}
				camino.get(indiceRetorno).setDirHastaCelda(movimientosPrevios.get(indiceRetorno));
				for(int aux2=movimientosPrevios.size()-1; aux2>indiceRetorno; aux2--) { // Limpia el ArrayList movimientosPrevios
					movimientosPrevios.remove(aux2);
				}
				
				generadorFrame.repintar();
				
			} else if(laberinto[f][c].getColor().equals(Color.GRAY)) {
				laberinto[f][c].setColor(Color.RED);
				movimientosPrevios.add(mover); // Añadimos cifra identificadora de movimiento al ArrayList movimientosPrevios
				generadorFrame.repintar(); // Repintamos para poder visualizar la nueva celda color rojo
			}
			
			try {
				Thread.sleep(TEMPORALIDAD); // Ayudara a construir mas deprisa o mas lento el laberinto
			} catch (InterruptedException e) {

			}
			
		} while(blanco==false);
		
	}
	
	/* Nombre: elegirSiguienteCelda
	 * Tipo: Metodo
	 * Funcion: Seleccionar aleatoriamente una nueva celda de inicio de camino
	 */
	public Celda elegirSiguienteCelda() {
		Random rand = new Random();
		Celda siguiente;
		do {
			siguiente = laberinto[rand.nextInt(laberinto.length)][rand.nextInt(laberinto[0].length)];
		} while(siguiente.isBlanca());
		return siguiente;
	}
	
}
