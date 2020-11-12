package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
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
	
	Frontera frontera = new Frontera();
	
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
		ArrayList<Celda> visitados=new ArrayList<Celda>();
		ArrayList<Nodo> frontera=new ArrayList<Nodo>();
		ArrayList<Nodo> aSolucion=new ArrayList<Nodo>();
		
		int id=padre.getId();
		int costo=padre.getCosto()+1;
		int profundidad=padre.getProfundidad()+1;
		boolean solucion=false;
		
		//Sacamos los valores de la fila/columna del objetivo para obtener la heuristica de Manhattan
		int fObjetivo= Integer.parseInt(objetive.substring(1, 2));
		int cObjetivo= Integer.parseInt(objetive.substring(3, 4));

		frontera.add(padre);
		long inicio = System.currentTimeMillis();
		
		while(!frontera.isEmpty() && !solucion) {
			Nodo nodo= frontera.remove(0);
			costo++;
			profundidad++;
					
			//Comprobamos si hemos llegado al objetivo 
			String fc="("+nodo.getEstado().getFila()+","+nodo.getEstado().getColumna()+")";
			if(objetive.equals(fc)) {			
				solucion=true;
			}else if(!visitados.contains(nodo.getEstado()) && nodo.getProfundidad()<profundidad){
					aSolucion.add(nodo);
					visitados.add(nodo.getEstado());
					Celda actual=funcionSucesores(nodo.getEstado(), laberinto);
				for(int i=actual.getSucesores().length-1;i>=0;i--) {
					try {
					Sucesor s1=actual.getSucesor(i);
					int heuristica= Math.abs(s1.getCelda().getFila() - fObjetivo) + Math.abs(s1.getCelda().getColumna() - cObjetivo);
					Nodo n = new Nodo(++id,costo+s1.getCelda().getValor(), s1.getCelda(), actual.getValor(), s1.getMov(), profundidad, heuristica, s1.getCelda().getValor());			
					frontera.add(n); 						
					}catch(NullPointerException e) {}
				}		
			}
		}
		
		long fin = System.currentTimeMillis();
        System.out.println("Tiempo de Ejecucion: "+(fin-inicio)+" MiliSegundos"); 
        mostrarSolucion(aSolucion,solucion,laberinto);			
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
	
	
	/* Nombre: SortbyValor
	* Tipo: Método
	* Función: Ordenar la frontera de menor a mayor segun el valor del Nodo
	*/
    public static class SortbyValor implements Comparator<Nodo> { 
        public int compare(Nodo a, Nodo b){ 
            return a.getEstado().getFila() - b.getEstado().getColumna(); 
        }     
    } 
    
	
	
	/* Nombre: funcionSucesores
	 * Tipo: Método
	 * Función: Generar estados sucesores de cada una de los estados (celdas) del laberinto (dependiendo de muros)
	 */
	public Celda funcionSucesores(Celda celda, Celda[][] laberinto) {
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
		return celda;
	} 

}
