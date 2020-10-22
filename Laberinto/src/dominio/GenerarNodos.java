package dominio;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/* Nombre: GeneradorNodos
 * Tipo: Clase
 * Funcion: Clase encargada de generar nodos aleatorios y añadirlos a la frontera
 */
public class GenerarNodos {
	
	
	/* Nombre: nodosAleatorios
	 * Tipo: Metodo
	 * Funcion: Crear 10 nodos aleatorios
	 */
	public void nodosAleatorios(String initial, String objective, JSONObject laberinto ) {
		Nodo n;
		Random rand= new Random();
		ArrayList<Nodo> frontera= new ArrayList<Nodo>();
		
		//Definimos las 4 direcciones para luego añadirlo de forma aleatoria a cada nodo
		String[] direcciones= new String[4];
		direcciones[0]="N";   direcciones[1]="E";   direcciones[2]="S";   direcciones[3]="O";
		int direccion=0;
		int costo=0;
		
		//Creamos 10 nodos aleatorios y lo añadimos a la frontera
		for(int i=0;i<10;i++) {
		    direccion=rand.nextInt(4);
			n= new Nodo(i+1, costo+1, i-1,i-1,direcciones[direccion],i,5,i);
			frontera.add(n);
		}	
		
		//Ordenamos la frontera segun el campor "valor" de los nodos
		Collections.sort(frontera,new ordenarFrontera());
		
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
