package dominio;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class GenerarNodos {

	public void generar(String initial, String objective, JSONObject laberinto) {
		Random rand = new Random();
		Frontera frontier=null;
		ArrayList<Nodo> frontera= new ArrayList<Nodo>();
		ArrayList<String> acciones= new ArrayList<String>();
		
		acciones.add("N");		acciones.add("E");		acciones.add("S");		acciones.add("O");
		for(int i=0;i<10;i++) {
		    int direccion =rand.nextInt(4);
		    String accion= acciones.get(direccion);
			Nodo n= new Nodo(i, i+1, rand.nextInt(5),rand.nextInt(3),accion,i-1, i, rand.nextInt(15));	
		}
		
	}
}
