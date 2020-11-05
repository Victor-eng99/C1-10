package dominio;

import java.util.ArrayList;
import java.util.Random;

import presentacion.InterfazPrincipal;

/* Nombre: GeneradorNodos
 * Tipo: Clase
 * Funcion: Clase encargada de generar nodos aleatorios y añadirlos a la frontera
 */
public class GenerarNodos {
	
	Frontera frontera = new Frontera();
	final int nodos=1000;
	
	/* Nombre: nodosAleatorios
	 * Tipo: Metodo
	 * Funcion: Crear nodos 
	 */
	public void nodos(String initial, String objective, Celda[][] laberinto,int opcion) {	
		int id=0;
		int profundidad=0;
		int costo=0;
		Nodo n= new Nodo(id, costo, laberinto[0][0], -1, "-", profundidad, 10, profundidad);
		
		// Buscamos el nodo inicial
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				String fc="("+laberinto[f][c].getFila()+","+laberinto[f][c].getColumna()+")";
				if(initial.equals(fc)) {
					Random rand = new Random();
					int fn= rand.nextInt(1001)+1;
				    n = new Nodo(id, costo, laberinto[f][c], -1, "-", profundidad, 10, profundidad);
					frontera.offer(n);
					laberinto[f][c].setIdNodo(id);
					//expandir(n, objective, laberinto);
				}
			}
		}
		//Elegimos el tipo de busqueda seleccionada al principio
		if(opcion==0) {
			anchura2(n,objective,laberinto);
		}/*else if(opcion==1) {
			aAsterisco(initial,objective,laberinto);
		}else if(opcion==2){
			profundidad(initial,objective,laberinto);
		}else if(opcion==3){
			costeUniforme(initial,objective,laberinto);
		}*/
	}
	
	/* Nombre: expandir
	 * Tipo: Método
	 * Función: Expansión de nodos
	 */
	public void anchura(Nodo padre,String objetive,Celda[][] laberinto) {
		ArrayList<Nodo> visitados=new ArrayList<Nodo>();
		int id=padre.getId();
		int costo=padre.getCosto()+1;
		int profundidad=padre.getProfundidad()+1;
		
		visitados.add(frontera.poll());
		visitados.get(id).getEstado().setExpandida(true);
		
		//Comprobamos si hemos llegado al objetivo y volvemos al menu principal
		String fc="("+visitados.get(visitados.size()-1).getEstado().getFila()+","+visitados.get(visitados.size()-1).getEstado().getColumna()+")";
		if(objetive.equals(fc)) {
			System.out.println("Se ha alcanzado el nodo objetivo");
			System.exit(0);
		}
			
		//Sacamos los sucesores de la celda actual para despues añadirlos a la frontera
		Celda actual=funcionSucesores(visitados.get(id).getEstado(), laberinto);
		System.out.println("Longitud "+actual.getSucesores().length);
		for(int i=actual.getSucesores().length-1;i>0;i--) {
			Sucesor s1=actual.getSucesor(i);
			Nodo n = new Nodo(++id,costo, laberinto[s1.getCelda().getFila()][s1.getCelda().getColumna()], -1, "-", profundidad, 10, profundidad);
			System.out.println(n.toString());
			//Si el elmento no ha sido añadido antes lo añadimos a la frontera
			if(!n.getEstado().isExpandida()) frontera.offer(n);			
		}
		
		anchura(frontera.poll(),objetive,laberinto);	
	}
	
	
	
	public ArrayList<Nodo> anchura2(Nodo padre,String objetive,Celda[][] laberinto) {
		ArrayList<Nodo> visitados=new ArrayList<Nodo>();
		ArrayList<Nodo> frontera=new ArrayList<Nodo>();
		int id=padre.getId();
		int costo=padre.getCosto()+1;
		int profundidad=padre.getProfundidad()+1;
		boolean solucion=false;
		
		frontera.add(padre);
		
		while(!frontera.isEmpty() && !solucion) {		
			padre=frontera.remove(0);
			
			//Comprobamos si hemos llegado al objetivo y volvemos al menu principal
			String fc="("+visitados.get(visitados.size()-1).getEstado().getFila()+","+visitados.get(visitados.size()-1).getEstado().getColumna()+")";
			if(objetive.equals(fc)) {
				System.out.println("Se ha alcanzado el nodo objetivo");
				solucion=true;
			}
			
			if(!visitados.contains(padre) && padre.getProfundidad()<profundidad) {
				visitados.add(padre);
				Celda actual=funcionSucesores(padre.getEstado(), laberinto);	
				for(int i=actual.getSucesores().length-1;i>0;i--) {
					Sucesor s1=actual.getSucesor(i);
					Nodo n = new Nodo(++id,costo, laberinto[s1.getCelda().getFila()][s1.getCelda().getColumna()], -1, "-", profundidad, 10, profundidad);
					frontera.add(n);
				}				
			}		
		}
		if(solucion) {
			return visitados;
		}else
			System.out.println("No hay solucion");
			
		return visitados;
	}
	
	
	/* Nombre: funcionSucesores
	 * Tipo: Método
	 * Función: Generar estados sucesores de cada una de los estados (celdas) del laberinto (dependiendo de muros)
	 */
	public Celda funcionSucesores(Celda celda, Celda[][] laberinto) {
		System.out.println("\nESTADO ("+celda.getFila()+","+celda.getColumna()+")");
		System.out.println("SUCESORES:");
		for(int m=0; m<celda.getMuros().length; m++) {
			if(celda.getMuro(m)==true && m==0) {
				Sucesor sucesor = new Sucesor("N",laberinto[celda.getFila()-1][celda.getColumna()], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(0, sucesor);
			}
			if(celda.getMuro(m)==true && m==1) {
				Sucesor sucesor = new Sucesor("E",laberinto[celda.getFila()][celda.getColumna()+1], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(1, sucesor);
			}
			if(celda.getMuro(m)==true && m==2) {
				Sucesor sucesor = new Sucesor("S",laberinto[celda.getFila()+1][celda.getColumna()], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(2, sucesor);
			}
			if(celda.getMuro(m)==true && m==3) {
				Sucesor sucesor = new Sucesor("O",laberinto[celda.getFila()][celda.getColumna()-1], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(3, sucesor);
			}
		}
		return celda;
	} 

}
