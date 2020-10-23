package presentacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


import org.json.JSONObject;

import dominio.LectorJSON;
import dominio.LectorJSONIOM;


/* Nombre: LecturaFichero
 * Tipo: Clase
 * Funcion: Leer fichero externo
 */
public class LecturaFichero {
	private static Scanner teclado = new Scanner(System.in);
	LectorJSON lectorJSON;
	LectorJSONIOM lectorJSONIOM;
	
	/* Nombre: LecturaFichero
	 * Tipo: Metodo
	 * Funcion: Constructor de clase LecturaFichero
	 */
	public LecturaFichero() {
		// Vacio
	}
	
	public void obtener(int token) throws IOException {
		JSONObject objetoCompleto = null;
		lectorJSON= new LectorJSON();	
		lectorJSONIOM = new LectorJSONIOM();
		
		Scanner s = pedirNombreFichero();		
		String cadena="";
		
        while(s.hasNext()) {
            cadena=cadena+s.next();
        }       
        s.close();		

		objetoCompleto = new JSONObject(cadena);
		
		// token = 0 (Entrega 1 (puzzle_NxM.json)), token = 1 (Entrega 2 (problema.json))
		if(token==0) {		
			// Enviamos el objeto JSON a la clase encargada de crear el array que representa el laberinto
			lectorJSON.leer(objetoCompleto); 	
		}else {	        
			lectorJSONIOM.leer(objetoCompleto);
		}
	}
	
	public Scanner pedirNombreFichero() {
		Scanner s = null;
		int ok=0;
		
		do {
			System.out.println("\nIntroduzca el nombre del fichero (sin extensión):");
			String nombre = teclado.next();
			try {
				s = new Scanner(new File(nombre+".json"));
				ok=1;
			} catch(FileNotFoundException e) {
				System.err.println("No se encontró el archivo especificado");
			}
		} while(ok!=1);
		
		return s;
	}
	

}
