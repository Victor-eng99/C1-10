package dominio;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class GenerarNodos {
	
	public void anchura(String initial, String objective, JSONObject laberinto ) {
		Celda inicial=new Celda(0,0);
		boolean objetivo=false;
		Celda siguiente;
		Sucesor s1;	
		ArrayList<Sucesor> sucesores=new ArrayList<Sucesor>();
		ArrayList<Nodo> frontera= new ArrayList<Nodo>();
		boolean [] muros= new boolean[4];
		
		Nodo n;
		int id=-1;
		int costo=0;
		int profundidad=0;
		
		do {
			muros=inicial.getMuros();
			if(muros[0]) {
				siguiente= new Celda(inicial.getFila()-1,inicial.getColumna());
				s1=new Sucesor("N",siguiente,1);
				n= new Nodo(id+1, costo+1, id-1,id-1,"N",profundidad,5,profundidad);	
				frontera.add(n);
			}
			if(muros[1]) {
				//siguiente=new Celda(inicial.getFila(),inicial.getColumna()+1);
				//s1=new Sucesor("E",siguiente,1);
				n=new Nodo(id+1,costo+1, id-1,id-1,"E",profundidad,5,profundidad);
				frontera.add(n);
			}
			if(muros[2]) {
				//siguiente=new Celda(inicial.getFila()-1,inicial.getColumna()+1);
				//s1=new Sucesor("S",siguiente,1);
				n=new Nodo(id+1,costo+1, id-1,id-1,"S",profundidad,5,profundidad);
				frontera.add(n);
			}
			if(muros[3]) {
				//siguiente=new Celda(inicial.getFila(),inicial.getColumna()-1);
				//s1=new Sucesor("O",siguiente,1);
				n=new Nodo(id+1,costo+1, id-1,id-1,"O",profundidad,5,profundidad);
				frontera.add(n);
			}
			
			Collections.sort(frontera,new ordenarFrontera());
			n=frontera.remove(frontera.size());
			
		}while(!objetivo);
		
	}

	/* Nombre: OrdenarFrontera
	 * Tipo: Metodo
	 * Funcion: Ordenamos en orden ascendente segun el valor de cada nodo
	 */
	 public static class ordenarFrontera implements Comparator<Nodo> { 
	        public int compare(Nodo a, Nodo b){ 
	            return b.getValor() - a.getValor(); 
	        }     
	    } 
	
	
}
