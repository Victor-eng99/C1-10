package dominio;

import org.json.JSONObject;

import presentacion.LecturaFichero;

/* Nombre: LectorJSONIOM
 * Tipo: Clase
 * Funcion: Obtener EI, EO y nombre de archivo JSON que contiene el laberinto
 */
public class LectorJSONIOM {
	
	/* Nombre: LectorJSONIOM
	 * Tipo: Metodo
	 * Funcion: COnstructor de la clase LectorJSONIOM
	 */
	public LectorJSONIOM() {
		// Vacio
	}
	
	/* Nombre: leer
	 * Tipo: Método
	 * Función: Leer el ficher JSON que contiene EI, EO y nombre de archivo JSON que contiene el laberinto
	 */
	public void leer(JSONObject jsoniom) {
		LecturaFichero lecturaFichero = new LecturaFichero();
		
		String initial = jsoniom.getString("INITIAL");
		String objective = jsoniom.getString("OBJECTIVE");
		String maze = jsoniom.getString("MAZE");
		
		lecturaFichero.obtenerMazeArbol(initial, objective, maze);
	}
}
