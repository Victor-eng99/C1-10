package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import dominio.AEstrella.SortbyValor;
import presentacion.InterfazPrincipal;

/* Nombre: GeneradorNodos
 * Tipo: Clase
 * Funcion: Clase encargada de generar nodos aleatorios y añadirlos a la frontera
 */
public class Anchura {
	
	/* Nombre: nodosAleatorios
	 * Tipo: Metodo
	 * Funcion: Crear nodos 
	 */
	public void nodoInicial(String initial, String objective, Celda[][] laberinto) {	
		int id=0;
		int costo=0;
		
		//Sacamos los valores de la fila/columna del objetivo para obtener la heuristica de Manhattan
		int fObjetivo= Integer.parseInt(objective.substring(1, 2));
		int cObjetivo= Integer.parseInt(objective.substring(4, 5));
		
		int fInicial= Integer.parseInt(initial.substring(1, 2));
		int cInicial= Integer.parseInt(initial.substring(3, 4));
				
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				String fc="("+laberinto[f][c].getFila()+","+laberinto[f][c].getColumna()+")";
				if(initial.equals(fc)) {
					int heuristica= Math.abs(fInicial - fObjetivo) + Math.abs(cInicial - cObjetivo);
				    Nodo n = new Nodo(id, costo, laberinto[f][c], -1, "-", 0, heuristica, 0);
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
		ArrayList<Celda> visitados=new ArrayList<Celda>();
		Comparator<Nodo> comparador= new OrdenarFrontera();
		PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>(1000,comparador);
		ArrayList<Nodo> aSolucion=new ArrayList<Nodo>();//ArrayList auxiliar para guardar la solucion y poder mostrarla
		ArrayList<Nodo> camino=new ArrayList<Nodo>();
		
		int id=padre.getId();
		int costo=padre.getCosto();
		int profundidad=padre.getProfundidad()+1;
		boolean solucion=false;
		
		//Sacamos los valores de la fila/columna del objetivo para obtener la heuristica de Manhattan
		int fObjetivo= Integer.parseInt(objetive.substring(1, 2));
		int cObjetivo= Integer.parseInt(objetive.substring(4, 5));

		frontera.add(padre);
		long inicio = System.currentTimeMillis();
		
		while(!frontera.isEmpty() && !solucion) {		
			Nodo nodo= frontera.poll();
					
			//Comprobamos si hemos llegado al objetivo 
			String fc="("+nodo.getEstado().getFila()+","+nodo.getEstado().getColumna()+")";
			if(objetive.equals(fc)) {
				visitados.add(nodo.getEstado());
				aSolucion.add(nodo);
				solucion=true;
				camino=mostrarCamino(aSolucion);
			}else if(!visitados.contains(nodo.getEstado()) && nodo.getProfundidad()<1000000){
					aSolucion.add(nodo);
					visitados.add(nodo.getEstado());
					funcionSucesores(nodo.getEstado(), laberinto);
				for(int i=0;i<nodo.getEstado().getSucesores().length;i++) {
					try {
					Sucesor s1=nodo.getEstado().getSucesor(i);
					if(s1.getCelda()!=null) {
						int heuristica= Math.abs(s1.getCelda().getFila() - fObjetivo) + Math.abs(s1.getCelda().getColumna() - cObjetivo);
						Nodo n = new Nodo(++id,nodo.getCosto()+s1.getCelda().getValor()+1, s1.getCelda(), nodo.getId(), s1.getMov(), nodo.getProfundidad()+1, heuristica, s1.getCostoMov()+nodo.getValor());			
						frontera.add(n); 		
					}
					}catch(NullPointerException e) {}
				}		
			}
			
		}	
		long fin = System.currentTimeMillis();
        System.out.println("\nTiempo de Ejecucion: "+(fin-inicio)+" MiliSegundos"); 
        Collections.sort(camino,new SortbyValor());
        mostrarSolucion(camino,solucion,laberinto);			
	}


	public ArrayList<Nodo> mostrarCamino(ArrayList<Nodo> nodosVisitados) {
		ArrayList<Nodo> solucion=new ArrayList<Nodo>();
		System.out.println("\nSOLUCIÓN INVERSA:");
		Nodo siguiente = nodosVisitados.get(nodosVisitados.size()-1);
		solucion.add(siguiente);
		System.out.println(siguiente.toString());
		while(siguiente.getIdPadre()!=-1) {
			for(int v=nodosVisitados.size()-1; v>=0; v--) {
				if(siguiente.getIdPadre()==nodosVisitados.get(v).getId()) {
					Nodo padre = nodosVisitados.get(v);
					solucion.add(padre);
					System.out.println(padre.toString());
					siguiente = padre;
				}
			}
		}
		return solucion;
	}
	
	/* Nombre: MostarSolucion
	* Tipo: Método
	* Función: Mostramos la solucion y llamamos a la clase encargada de generar el .TXT
	*/
	public void mostrarSolucion(ArrayList<Nodo> aSolucion,boolean solucion,Celda[][] laberinto) {	
		if(solucion) {		
			System.out.println("\n\u001B[32mSe ha alcanzado el nodo objetivo");
			GeneradorTXT gt=new GeneradorTXT();
			gt.generarTXT(laberinto,"BREADTH",aSolucion);
		}else
			System.out.println("\u001B[31mNo hay solucion");			
	}
	
	
	/* Nombre: funcionSucesores
	 * Tipo: Método
	 * Función: Generar estados sucesores de cada una de los estados (celdas) del laberinto (dependiendo de muros)
	 */
	public void funcionSucesores(Celda celda, Celda[][] laberinto) {
		for(int m=0; m<celda.getMuros().length; m++) {
			if(celda.getMuro(m)==true && m==0) {
				Sucesor sucesor = new Sucesor("N",laberinto[celda.getFila()-1][celda.getColumna()], 1);
				celda.setSucesores(0, sucesor);
			}
			if(celda.getMuro(m)==true && m==1) {
				Sucesor sucesor = new Sucesor("E",laberinto[celda.getFila()][celda.getColumna()+1], 1);
				celda.setSucesores(1, sucesor);
			}
			if(celda.getMuro(m)==true && m==2) {
				Sucesor sucesor = new Sucesor("S",laberinto[celda.getFila()+1][celda.getColumna()], 1);
				celda.setSucesores(2, sucesor);
			}
			if(celda.getMuro(m)==true && m==3) {
				Sucesor sucesor = new Sucesor("O",laberinto[celda.getFila()][celda.getColumna()-1], 1);
				celda.setSucesores(3, sucesor);
			}
		}
	} 

}
