package busquedas;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import dominio.Celda;
import dominio.GeneradorTXT;
import dominio.Nodo;
import dominio.Sucesor;
import presentacion.GeneradorPNG;

/* Nombre: GeneradorNodos
 * Tipo: Clase
 * Funcion: Clase encargada de generar nodos aleatorios y añadirlos a la frontera
 */
public class Voraz {
	GeneradorPNG generadorPNG;
	
	public void nodoInicial(String initial, String objetive, Celda[][] laberinto) {
		int id=0;
		int profundidad=0;
		
		//Sacamos los valores de la fila/columna del objetivo para obtener la heuristica de Manhattan
		String cadena= objetive.substring(objetive.indexOf("(")+1,objetive.indexOf(")"));
		String[] s= cadena.split(",");
		int fObjetivo=Integer.parseInt(s[0]);
		int cObjetivo= Integer.parseInt(s[1]);
		
		pintarCeldas(laberinto, 0);
	
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				String fc="("+laberinto[f][c].getFila()+","+laberinto[f][c].getColumna()+")";
				if(initial.equals(fc)) {
					int heuristica= Math.abs(laberinto[f][c].getFila() - fObjetivo) + Math.abs(laberinto[f][c].getColumna() - cObjetivo);
				    Nodo n = new Nodo(id, 0, laberinto[f][c], -1, "-", profundidad, heuristica, heuristica);			
					voraz(n,objetive,laberinto,fObjetivo,cObjetivo);
				}
			}
		}
	}
	
	/* Nombre: voraz
	 * Tipo: Metodo
	 * Funcion: Implementacion de algoritmo principal de busqueda voraz
	 */
	public void voraz(Nodo nodo, String objetive, Celda[][] laberinto,int fObjetivo,int cObjetivo) {
		ArrayList<Celda> visitados=new ArrayList<Celda>();
		ArrayList<Nodo> nodosVisitados=new ArrayList<Nodo>();
		
		Comparator<Nodo> comparador= new OrdenarFrontera();
		PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>(1000,comparador);
		
		boolean solucion=false;
		int id=0;
		
		frontera.add(nodo);
		while(!solucion && !frontera.isEmpty()) {
			Nodo padre = frontera.poll();
			String fc="("+padre.getEstado().getFila()+","+padre.getEstado().getColumna()+")";
			if(objetive.equals(fc)) {			
				solucion=true; // Hemos alcanzado el objetivo
				visitados.add(padre.getEstado());
				nodosVisitados.add(padre);
				for(Celda c : visitados) {
					c.setColor(Color.GREEN);
				}
				mostrarCamino(nodosVisitados, laberinto);
			} else {
				if(!visitados.contains(padre.getEstado())) {
					funcionSucesores(padre.getEstado(), laberinto);
					for(int i=0; i<padre.getEstado().getSucesores().length; i++) {
						try {
							Sucesor s=padre.getEstado().getSucesor(i);
							int heuristica= Math.abs(s.getCelda().getFila() - fObjetivo) + Math.abs(s.getCelda().getColumna() - cObjetivo);
							Nodo n = new Nodo(++id, s.getCostoMov()+padre.getCosto(), s.getCelda(), padre.getId(), s.getMov(), padre.getProfundidad()+1, heuristica, heuristica);
							frontera.add(n);
							n.getEstado().setColor(Color.BLUE);
						}catch(NullPointerException e) {}
					}
				}
				visitados.add(padre.getEstado()); // Nodo expandido = Su estado ha sido visitado
				nodosVisitados.add(padre);
			}

		}

	}
	
	public void mostrarCamino(ArrayList<Nodo> nodosVisitados, Celda[][] laberinto) {
		ArrayList<Nodo> sol = new ArrayList<Nodo>();
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		Nodo siguiente = nodosVisitados.get(nodosVisitados.size()-1);
		siguiente.getEstado().setColor(Color.RED);
		while(siguiente.getIdPadre()!=-1) {
			for(int v=0; v<nodosVisitados.size(); v++) {
				if(siguiente.getIdPadre()==nodosVisitados.get(v).getId()) {
					Nodo padre = nodosVisitados.get(v);
					sol.add(padre);
					padre.getEstado().setColor(Color.RED);
					siguiente = padre;
				}
			}
		}
		pintarCeldas(laberinto, 1);
		System.out.println("\nSOLUCIÓN:");
		System.out.println("[id][cost,state,father_id,action,depth,h,value]");
		for(int s=sol.size()-1; s>=0; s--) {
			solucion.add(sol.get(s));
			System.out.println(sol.get(s).toString());
		}
		solucion.add(nodosVisitados.get(nodosVisitados.size()-1));
		System.out.println(nodosVisitados.get(nodosVisitados.size()-1));
		mostrarSolucion(solucion, laberinto);
	}
	
	/* Nombre: mostrarSolucion
	* Tipo: Metodo
	* Función: Mostramos la solucion y llamamos a la clase encargada de generar el .TXT
	*/
	public void mostrarSolucion(ArrayList<Nodo> aSolucion, Celda[][] laberinto) {	
		System.out.println("\n\u001B[32mSe ha alcanzado el nodo objetivo");
		GeneradorTXT gt=new GeneradorTXT();
		gt.generarTXT(laberinto,"GREEDY",aSolucion);
	}
	
	/* Nombre: funcionSucesores
	 * Tipo: Metodo
	 * Función: Generar estados sucesores de cada una de los estados (celdas) del laberinto (dependiendo de muros)
	 */
	public void funcionSucesores(Celda celda, Celda[][] laberinto) {
		for(int m=0; m<celda.getMuros().length; m++) {
			if(celda.getMuro(m)==true && m==0) {
				Sucesor sucesor = new Sucesor("N",laberinto[celda.getFila()-1][celda.getColumna()], laberinto[celda.getFila()-1][celda.getColumna()].getValor()+1);
				celda.setSucesores(0, sucesor);
			}
			if(celda.getMuro(m)==true && m==1) {
				Sucesor sucesor = new Sucesor("E",laberinto[celda.getFila()][celda.getColumna()+1], laberinto[celda.getFila()][celda.getColumna()+1].getValor()+1);
				celda.setSucesores(1, sucesor);
			}
			if(celda.getMuro(m)==true && m==2) {
				Sucesor sucesor = new Sucesor("S",laberinto[celda.getFila()+1][celda.getColumna()], laberinto[celda.getFila()+1][celda.getColumna()].getValor()+1);
				celda.setSucesores(2, sucesor);
			}
			if(celda.getMuro(m)==true && m==3) {
				Sucesor sucesor = new Sucesor("O",laberinto[celda.getFila()][celda.getColumna()-1], laberinto[celda.getFila()][celda.getColumna()-1].getValor()+1);
				celda.setSucesores(3, sucesor);
			}
		}
	}
	
	public void pintarCeldas(Celda[][] laberinto, int token) {
		generadorPNG = new GeneradorPNG();
		
		if(token==0) { // token = 0 -> Inicial
			for(int i=0; i<laberinto.length; i++) {
				for(int j=0; j<laberinto[0].length; j++) {
					if(laberinto[i][j].getValor()==0) {
						laberinto[i][j].setColor(Color.WHITE);
					}if(laberinto[i][j].getValor()==1) {
						Color cGris=new Color(241,231,186);
						laberinto[i][j].setColor(cGris);
					}if(laberinto[i][j].getValor()==2) {
						Color cVerde=new Color(147,235,145);
						laberinto[i][j].setColor(cVerde);
					}if(laberinto[i][j].getValor()==3) {
						Color cAzul=new Color(186,224,241);
						laberinto[i][j].setColor(cAzul);
					}
				}
			}
			generadorPNG.generar(laberinto, "puzzle_loop_"+laberinto.length+"x"+laberinto[0].length+"_20.png");
		} else {
			generadorPNG.generar(laberinto, "solution_"+laberinto.length+"x"+laberinto[0].length+"_GREEDY_20.png");
		}
	}


}

