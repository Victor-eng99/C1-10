package dominio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
		LectorJSON lectorJSON= new LectorJSON();
		
		String initial = jsoniom.getString("INITIAL");
		String objective = jsoniom.getString("OBJECTIVE");
		String maze = jsoniom.getString("MAZE");
		
		Scanner s = null;		
		try {
			s = new Scanner(new File(maze));
		} catch (FileNotFoundException e) {
			System.err.println("No se encontró el archivo especificado");
		}		
		String cadena="";		
		while(s.hasNext()) {
            cadena=cadena+s.next();
        } 
        s.close();	
		JSONObject objetoCompleto = new JSONObject(cadena);		
		
		// Enviamos el objeto JSON a la clase encargada de crear el array que representa el laberinto (+ EI y EO)
		lectorJSON.leerMazeArbol(initial, objective, objetoCompleto); 
	}
}
