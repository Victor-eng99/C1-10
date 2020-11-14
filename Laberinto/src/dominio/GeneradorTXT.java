package dominio;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GeneradorTXT {

	
	/* Nombre: GeneradorTXT
	 * Tipo: Metodo
	 * Funcion:
	 */
	public GeneradorTXT() {
		// Vacio
	}
	
	
	/* Nombre: generarTXT
	 * Tipo: Metodo
	 * Funcion: Generacion del fichero TXT que representa la solucion de la busqueda
	 */
	public void generarTXT(Celda[][] laberinto,String busqueda,ArrayList<Nodo> aSolucion) {
		try {
			FileWriter file = new FileWriter("sol_"+laberinto.length+"x"+ laberinto[0].length+"_"+busqueda+".txt");
			file.write("[id][cost,state,father_id,action,depth,h,value]\n");
			for(int i=0;i<aSolucion.size()-1;i++) {
				//file.write(aSolucion.get(i).toString()+"\n");
			}
			file.flush();
			file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }		
	}
	
	
	
}
	

