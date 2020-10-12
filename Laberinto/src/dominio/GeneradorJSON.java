package dominio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.*;

/* Nombre: GeneradorJSON
 * Tipo: Clase
 * Funcion: CLase encargada de crer un archivo JSON a partir de un objeto que albergue características del laberinto
 */
public class GeneradorJSON {
	LectorJSON lectorJSON;
	
	/* Nombre: GeneradorJSON
	 * Tipo: Método
	 * Funcion: Constructor de la clase GeneradorJSON
	 */
	public GeneradorJSON() {
		// Vacio
	}
	
	/* Nombre: generar
	 * Tipo: Metodo
	 * Función: Creación y escritura del archivo JSON
	 */
	public void generar(Celda[][] laberinto, int[] parametros, ArrayList<int[]> movimientos, ArrayList<String> idMov) throws IOException {
		lectorJSON = new LectorJSON();
		JSONObject objLaberinto = crearLaberinto(laberinto, parametros, movimientos, idMov);

		try {

			FileWriter file = new FileWriter("laberinto_"+laberinto.length+"x"+laberinto[0].length+".json");
			file.write(objLaberinto.toString());
			file.flush();
			file.close();

		} catch (IOException e) {
			System.err.print("Ocurrió un problema creado el fichero");
		}
		
		lectorJSON.leer(objLaberinto); // Enviamos el objeto a la clase que lo leera y construira un array a partir de el
        
	}
	
	/* Nombre: crearLaberinto
	 * Tipo: Metodo
	 * Funcion: Creación del JSONObject que albergara la representacion del laberinto
	 */
    public JSONObject crearLaberinto(Celda[][] laberinto, int[] parametros, ArrayList<int[]> movimientos, ArrayList<String> idMov) {
    	JSONObject objetoCompleto = new JSONObject();
    	JSONObject objCeldas = new JSONObject();
    	int rows;
    	int cols;
    	int max_n;
    	JSONArray mov = new JSONArray();
    	JSONArray id_mov = new JSONArray();
        
    	rows = parametros[0];
    	cols = parametros[1];
    	max_n = laberinto[0][0].getMaxVecinos();    	
    	
    	for(int auxMov=0; auxMov<movimientos.size(); auxMov++) { // Carga de JSONArray que guarda movimientos en formato [x,y]
    		mov.put(movimientos.get(auxMov));
    	}
    	
    	for(int auxIdMov=0; auxIdMov<idMov.size(); auxIdMov++) { // Carga de JSONArray que guarda movimientos por id
    		id_mov.put(idMov.get(auxIdMov));
    	}
    	
    	objCeldas = crearCeldas(laberinto);
    	
    	objetoCompleto.put("rows", rows);
    	objetoCompleto.put("cols", cols);
    	objetoCompleto.put("max_n", max_n);
    	objetoCompleto.put("mov", mov);
    	objetoCompleto.put("id_mov", id_mov);
    	objetoCompleto.put("cells", objCeldas);
    	
    	return objetoCompleto;
    }
    
    /* Nombre crearCeldas
     * Tipo: Metodo
     * Funcion: Devolver el JSONObject objetoCeldas el cual contiene a su vez multiples objetos (i,j), representando cada uno
     * cada celda individual que conforma el laberinto
     */
    public JSONObject crearCeldas(Celda[][] laberinto) {
    	JSONObject objetoCeldas = new JSONObject(); // El JSONObject objetoCeldas guarda a su vez el JSONObject (i,j)
    	
    	for(int i=0; i<laberinto.length; i++) {
    		for(int j=0; j<laberinto[0].length; j++) {
    			JSONObject posicion = new JSONObject(); // El JSONObject posicion guarda a su vez el JSONObject value y el JSONArray muros
    			JSONArray muros = new JSONArray();
    			
    			for(int m=0; m<laberinto[i][j].getMaxVecinos(); m++) { // Cargamos el JSONArray muros para cada celda individual
    	    		muros.put(laberinto[i][j].getMuro(m));
    	    	}
    			
    			posicion.put("value", laberinto[i][j].getValor());
    			posicion.put("neighbors", muros);
    			
    			objetoCeldas.put("("+i+","+j+")", posicion);
    		}
    	}
    
    	return objetoCeldas;
    }

}
