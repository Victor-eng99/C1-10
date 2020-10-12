package presentacion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


import org.json.JSONObject;

import dominio.LectorJSON;

/* Nombre: LecturaFichero
 * Tipo: Clase
 * Funcion: Leer fichero externo
 */
public class LecturaFichero {
	private static Scanner teclado = new Scanner(System.in);
	LectorJSON lectorJSON;
	
	/* Nombre: LecturaFichero
	 * Tipo: Metodo
	 * Funcion: Constructor de clase LecturaFichero
	 */
	public LecturaFichero() {
		// Vacio
	}
	
	public void obtener() throws IOException {
		Scanner s = null;
		int ok=0;
		lectorJSON= new LectorJSON();
		
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
		
		String cadena="";
		
        while(s.hasNextLine()) {
            cadena=cadena+s.next();
        }
        
        s.close();
		
		JSONObject objetoCompleto = new JSONObject(cadena);
		
        lectorJSON.leer(objetoCompleto); // Enviamos el objeto JSON a la clase encargada de crear el array que representa el laberinto
	}
	
	
	

}
