package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;



/* Nombre: GeneradorNodos
 * Tipo: Clase
 * Funcion: Clase encargada de generar nodos aleatorios y añadirlos a la frontera
 */
public class Anchura {
	
	/* Nombre: NodoInicial
	 * Tipo: Metodo
	 * Funcion: Encontrar el nodo inicial y comenzar la busqueda
	 */
	public void nodoInicial(String initial, String objective, Celda[][] laberinto) {	
		int id=0;
		int costo=0;
		
		//Sacamos los valores de la fila/columna del objetivo para obtener la heuristica de Manhattan
		int fObjetivo= Integer.parseInt(objective.substring(1, 2));
		int cObjetivo= Integer.parseInt(objective.substring(3, 4));
		
		int fInicial= Integer.parseInt(initial.substring(1, 2));
		int cInicial= Integer.parseInt(initial.substring(3, 4));
				
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				String fc="("+laberinto[f][c].getFila()+","+laberinto[f][c].getColumna()+")";
				if(initial.equals(fc)) {
					int heuristica= Math.abs(fInicial - fObjetivo) + Math.abs(cInicial - cObjetivo);
				    Nodo n = new Nodo(id, costo, laberinto[f][c], -1, "-", 0, heuristica, 0);
					anchura(n,objective,laberinto);
				}
			}
		}
	}
	
	/* Nombre: anchura
	 * Tipo: Metodo
	 * Función: Busqueda en anchura a partir del nodo inicial hasta el objetivo
	 */
	public void anchura(Nodo padre,String objetive,Celda[][] laberinto) {
		ArrayList<Celda> visitados=new ArrayList<Celda>();
		ArrayList<Nodo> aSolucion=new ArrayList<Nodo>();//ArrayList auxiliar para guardar la solucion y poder mostrarla
		
		Comparator<Nodo> comparador= new OrdenarFrontera();
		PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>(1000,comparador);
		
		int id=padre.getId();
		boolean solucion=false;
		
		//Sacamos los valores de la fila/columna del objetivo para obtener la heuristica de Manhattan
		int fObjetivo= Integer.parseInt(objetive.substring(1, 2));
		int cObjetivo= Integer.parseInt(objetive.substring(3, 4));

		frontera.add(padre);
		
		while(!frontera.isEmpty() && !solucion) {		
			Nodo nodo= frontera.poll();
					
			//Comprobamos si hemos llegado al objetivo 
			String fc="("+nodo.getEstado().getFila()+","+nodo.getEstado().getColumna()+")";
			if(objetive.equals(fc)) {
				visitados.add(nodo.getEstado());
				aSolucion.add(nodo);
				solucion=true;
				mostrarCamino(aSolucion, laberinto);
			}else if(!visitados.contains(nodo.getEstado()) && nodo.getProfundidad()<1000000){
					aSolucion.add(nodo);
					visitados.add(nodo.getEstado());
					funcionSucesores(nodo.getEstado(), laberinto);
				for(int i=0;i<nodo.getEstado().getSucesores().length;i++) {
					try {
						Sucesor s1=nodo.getEstado().getSucesor(i);
						int heuristica= Math.abs(s1.getCelda().getFila() - fObjetivo) + Math.abs(s1.getCelda().getColumna() - cObjetivo);
						Nodo n = new Nodo(++id,nodo.getCosto()+s1.getCelda().getValor()+1, s1.getCelda(), nodo.getId(), s1.getMov(), nodo.getProfundidad()+1, heuristica, s1.getCostoMov()+nodo.getValor());			
						frontera.add(n); 		
					}catch(NullPointerException e) {}
				}		
			}		
		}		
	}

	public void mostrarCamino(ArrayList<Nodo> nodosVisitados, Celda[][] laberinto) {
		ArrayList<Nodo> sol = new ArrayList<Nodo>();
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		Nodo siguiente = nodosVisitados.get(nodosVisitados.size()-1);
		while(siguiente.getIdPadre()!=-1) {
			for(int v=0; v<nodosVisitados.size(); v++) {
				if(siguiente.getIdPadre()==nodosVisitados.get(v).getId()) {
					Nodo padre = nodosVisitados.get(v);
					sol.add(padre);
					siguiente = padre;
				}
			}
		}
		System.out.println("\nSOLUCIÓN:");
		System.out.println("[id][cost,state,father_id,action,depth,h,value]");
		for(int s=sol.size()-1; s>=0; s--) {
			solucion.add(sol.get(s));
			System.out.println(sol.get(s).toString());
		}
		solucion.add(nodosVisitados.get(nodosVisitados.size()-1));
		System.out.println(nodosVisitados.get(nodosVisitados.size()-1));
		mostrarSolucion(solucion, laberinto);
	}
	
	/* Nombre: mostrarSolucion
	* Tipo: Metodo
	* Función: Mostramos la solucion y llamamos a la clase encargada de generar el .TXT
	*/
	public void mostrarSolucion(ArrayList<Nodo> aSolucion, Celda[][] laberinto) {	
		System.out.println("\n\u001B[32mSe ha alcanzado el nodo objetivo");
		GeneradorTXT gt=new GeneradorTXT();
		gt.generarTXT(laberinto,"BREADTH",aSolucion);
	}
	
	/* Nombre: funcionSucesores
	 * Tipo: Metodo
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
