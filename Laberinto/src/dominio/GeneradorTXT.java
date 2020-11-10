package dominio;

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
			PrintWriter writer = new PrintWriter("sol_"+laberinto.length+"x"+ laberinto[0].length+"_"+busqueda+".txt", "UTF-8");
			writer.println("[id][cost,state,father_id,action,depth,h,value]");
			for(int i=0;i<aSolucion.size()-1;i++) {
				writer.println(aSolucion.get(i).toString());
			}
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }		
	}
	
	
	
}
	

