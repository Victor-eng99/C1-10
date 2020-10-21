package dominio;

import java.util.ArrayList;

/* Nombre: Frontera
 * Tipo: Clase
 * Funcion: Clase que representa la frontera del arbol
 */
public class Frontera {

	private int fila;
	private int columna;
	
	
	public Frontera(int fila, int columna) {
		super();
		this.fila = fila;
		this.columna = columna;
	}
	
	public void Insertar(ArrayList<Nodo> frontera,Nodo n1) {
		frontera.add(n1);
	}
	
	public void Eliminar(ArrayList<Nodo> frontera,Nodo n1) {
		frontera.remove(n1);
	}
	
	
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public int getColumna() {
		return columna;
	}
	public void setColumna(int columna) {
		this.columna = columna;
	}
	
	
}
