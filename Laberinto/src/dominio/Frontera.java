package dominio;

import java.util.ArrayList;
import java.util.Collections;


/* Nombre: Frontera
 * Tipo: Clase
 * Funcion: Clase que representa la frontera del arbol
 */
public class Frontera {

	ArrayList<Nodo> frontera = new ArrayList<Nodo>();
	
	public Frontera() {
		// Vacío
	}
	
	public void offer(Nodo n) {
		frontera.add(n);
	}
	
	public Nodo poll() {
		Nodo n = frontera.get(0);
		frontera.remove(0);
		return n;
	}
	
	public boolean isEmpty() {
		return frontera.isEmpty();
	}
	
	public void ordenar() {
		Collections.sort(frontera);
	}

	
}
