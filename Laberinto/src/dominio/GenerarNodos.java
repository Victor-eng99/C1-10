package dominio;

import java.util.Random;

/* Nombre: GeneradorNodos
 * Tipo: Clase
 * Funcion: Clase encargada de generar nodos aleatorios y añadirlos a la frontera
 */
public class GenerarNodos {
	
	Frontera frontera = new Frontera();
	final int nodos=10000000;
	
	/* Nombre: nodosAleatorios
	 * Tipo: Metodo
	 * Funcion: Crear nodos 
	 */
	public void nodos(String initial, String objective, Celda[][] laberinto ) {
		int id=0;
		int profundidad=0;
		int costo=0;
		
		generarSucesores(laberinto);
		
		// Comenzamos a generar a partir del estado inicial
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				String fc="("+laberinto[f][c].getFila()+","+laberinto[f][c].getColumna()+")";
				if(initial.equals(fc)) {
					Random rand = new Random();
					int fn= rand.nextInt(10000001)+1;
					Nodo n = new Nodo(id, costo, laberinto[f][c], -1, "-", profundidad, 10, fn);
					frontera.offer(n);
					laberinto[f][c].setIdNodo(id);
					System.out.println("\nGENERANDO NODOS...");
					System.out.println(n.toString());
					expandir(n, objective, laberinto);
				}
			}
		}
		
	}
	
	/* Nombre: expandir
	 * Tipo: Método
	 * Función: Expansión de nodos
	 * ***NOTA: Al no contar con una estrategia en esta entrega, se usará el método para insertar el mismo nodo "x" (nodos) veces,
	 * de forma que podamos someter a la frontera a un test de estrés y medir tiempos.
	 */
	public void expandir(Nodo padre, String objective, Celda[][] laberinto) {
		int id=padre.getId();
		int costo=padre.getCosto();
		int profundidad=padre.getProfundidad();
		
		long tiempoInicial=System.currentTimeMillis();
		for(int i=0; i<nodos; i++) {
			Random rand = new Random();
			int fn= rand.nextInt(10000001)+1;
			Nodo n = new Nodo(++id, costo, padre.getEstado(), -1, "-", profundidad, 10, fn);
			System.out.println(n.toString());
			frontera.offer(n);
		}
		long tiempoFinal=System.currentTimeMillis();
		
		long total = tiempoFinal-tiempoInicial;
		System.out.println("\nTiempo en insertar " + nodos + " nodos: " + total/1000 + " segundos");
		/*
		frontera.poll();
		padre.getEstado().setExpandida(true);
		for(int s=0; s<padre.getEstado().getSucesores().length; s++) {
			if(padre.getEstado().getSucesor(s)!=null) {
				Random rand = new Random();
				int fn = rand.nextInt(100001)+1;
				
			}
		}
		*/
		
	}
	
	/* Nombre: generarSucesores
	 * Tipo: Método
	 * Función: Llamada iterativa a funcionSucesores
	 */
	public void generarSucesores(Celda[][] laberinto) {
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				funcionSucesores(laberinto[f][c], laberinto);
			}
		}
	}
	
	/* Nombre: funcionSucesores
	 * Tipo: Método
	 * Función: Generar estados sucesores de cada una de los estados (celdas) del laberinto (dependiendo de muros)
	 */
	public void funcionSucesores(Celda celda, Celda[][] laberinto) {
		System.out.println("\nESTADO ("+celda.getFila()+","+celda.getColumna()+")");
		System.out.println("SUCESORES:");
		for(int m=0; m<celda.getMuros().length; m++) {
			if(celda.getMuro(m)==true && m==0) {
				Sucesor sucesor = new Sucesor("N",laberinto[celda.getFila()-1][celda.getColumna()], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(0, sucesor);
			}
			else if(celda.getMuro(m)==true && m==1) {
				Sucesor sucesor = new Sucesor("E",laberinto[celda.getFila()][celda.getColumna()+1], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(1, sucesor);
			}
			else if(celda.getMuro(m)==true && m==2) {
				Sucesor sucesor = new Sucesor("S",laberinto[celda.getFila()+1][celda.getColumna()], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(2, sucesor);
			}
			else if(celda.getMuro(m)==true && m==3) {
				Sucesor sucesor = new Sucesor("O",laberinto[celda.getFila()][celda.getColumna()-1], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(3, sucesor);
			}
		}
	} 

}
