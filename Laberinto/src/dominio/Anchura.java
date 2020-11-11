package dominio;

import java.util.ArrayList;
import java.util.Random;

import presentacion.InterfazPrincipal;

/* Nombre: GeneradorNodos
 * Tipo: Clase
 * Funcion: Clase encargada de generar nodos aleatorios y añadirlos a la frontera
 */
public class Anchura {
	
	ArrayList<Nodo> frontera = new ArrayList<Nodo>();
	
	/* Nombre: nodosAleatorios
	 * Tipo: Metodo
	 * Funcion: Crear nodos 
	 */
	public void nodoInicial(String initial, String objective, Celda[][] laberinto) {	
		int id=0;
		int profundidad=0;
		int costo=0;
		
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				String fc="("+laberinto[f][c].getFila()+","+laberinto[f][c].getColumna()+")";
				if(initial.equals(fc)) {
				    Nodo n = new Nodo(id, costo, laberinto[f][c], -1, "-", profundidad, 10, profundidad);
					laberinto[f][c].setIdNodo(id);
					anchura(n,objective,laberinto);
				}
			}
		}
	}
	
	/* Nombre: anchura
	 * Tipo: Método
	 * Función: Busqueda en anchura a partir del nodo inicial hasta el objetivo
	 */
	public void anchura(Nodo padre,String objetive,Celda[][] laberinto) {
		ArrayList<Nodo> visitados=new ArrayList<Nodo>();
		ArrayList<Nodo> frontera=new ArrayList<Nodo>();
		int id=padre.getId();
		int costo=padre.getCosto()+1;
		int profundidad=padre.getProfundidad()+1;
		boolean solucion=false;
		
		frontera.add(padre);
		long inicio = System.currentTimeMillis();
		while(!frontera.isEmpty() && !solucion) {		
			visitados.add(frontera.remove(0));
			costo++;
			profundidad++;
			//Comprobamos si hemos llegado al objetivo y volvemos al menu principal
			String fc="("+visitados.get(visitados.size()-1).getEstado().getFila()+","+visitados.get(visitados.size()-1).getEstado().getColumna()+")";
			if(objetive.equals(fc)) {			
				solucion=true;
			}
			
			int sucesores=funcionSucesores(visitados.get(visitados.size()-1).getEstado(), laberinto);
			Celda actual=visitados.get(visitados.size()-1).getEstado();
			for(int i=sucesores-1;i>0;i--) {
				try {
				Sucesor s1=actual.getSucesor(i);
				Nodo n = new Nodo(++id,costo, s1.getCelda(), -1, "-", costo, 10, profundidad);			
					frontera.add(n);						
				}catch(NullPointerException e) {}
			}		
		}
		
		long fin = System.currentTimeMillis();
        System.out.println("Tiempo de Ejecucion: "+(fin-inicio)+" MiliSegundos");
		if(solucion) {
			System.out.println("\n\u001B[32mSe ha alcanzado el nodo objetivo");
		}else
			System.out.println("\u001B[31mNo hay solucion");
			
	}
	
	
	
	/* Nombre: contiene
	 * Tipo: Método
	 * Función: Nos dice si un nodo existe o no en un ArrayList
	 */
	public boolean contiene(ArrayList<Nodo> visitados,Nodo n) {
		boolean encontrado=false;
		
		for(int i=visitados.size()-1;i>0;i--) {
			if(n.getEstado().getFila()==visitados.get(i).getEstado().getFila() && 
					n.getEstado().getColumna()==visitados.get(i).getEstado().getColumna()) {
						encontrado=true;
			}
		}	
		return encontrado;
	}
	
	
	
	/* Nombre: funcionSucesores
	 * Tipo: Método
	 * Función: Generar estados sucesores de cada una de los estados (celdas) del laberinto (dependiendo de muros)
	 */
	public int funcionSucesores(Celda celda, Celda[][] laberinto) {
		int sucesores=0;
		System.out.println("\nESTADO ("+celda.getFila()+","+celda.getColumna()+")");
		System.out.println("SUCESORES:");
		for(int m=0; m<celda.getMuros().length; m++) {
			if(celda.getMuro(m)==true && m==0) {
				Sucesor sucesor = new Sucesor("N",laberinto[celda.getFila()-1][celda.getColumna()], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(0, sucesor);
				sucesores++;
			}
			if(celda.getMuro(m)==true && m==1) {
				Sucesor sucesor = new Sucesor("E",laberinto[celda.getFila()][celda.getColumna()+1], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(1, sucesor);
				sucesores++;
			}
			if(celda.getMuro(m)==true && m==2) {
				Sucesor sucesor = new Sucesor("S",laberinto[celda.getFila()+1][celda.getColumna()], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(2, sucesor);
				sucesores++;
			}
			if(celda.getMuro(m)==true && m==3) {
				Sucesor sucesor = new Sucesor("O",laberinto[celda.getFila()][celda.getColumna()-1], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(3, sucesor);
				sucesores++;
			}
		}
		return sucesores;
	} 

}
