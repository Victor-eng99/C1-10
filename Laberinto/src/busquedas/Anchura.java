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
 * Funcion: Clase encargada de generar nodos aleatorios y a�adirlos a la frontera
 */
public class Anchura {
	GeneradorPNG generadorPNG;
	
	/* Nombre: NodoInicial
	 * Tipo: Metodo
	 * Funcion: Encontrar el nodo inicial y comenzar la busqueda
	 */
	public void nodoInicial(String initial, String objetive, Celda[][] laberinto) {	
		int id=0;
		int costo=0;
				
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
				    Nodo n = new Nodo(id, costo, laberinto[f][c], -1, "-", 0, heuristica, 0);
					anchura(n,objetive,laberinto,fObjetivo,cObjetivo);
				}
			}
		}
	}
	
	/* Nombre: anchura
	 * Tipo: Metodo
	 * Funci�n: Busqueda en anchura a partir del nodo inicial hasta el objetivo
	 */
	public void anchura(Nodo padre,String objetive,Celda[][] laberinto,int fObjetivo,int cObjetivo) {
		ArrayList<Celda> visitados=new ArrayList<Celda>();
		ArrayList<Nodo> aSolucion=new ArrayList<Nodo>();//ArrayList auxiliar para guardar la solucion y poder mostrarla
		
		Comparator<Nodo> comparador= new OrdenarFrontera();
		PriorityQueue<Nodo> frontera = new PriorityQueue<Nodo>(1000,comparador);
		
		int id=padre.getId();
		boolean solucion=false;

		frontera.add(padre);
		while(!frontera.isEmpty() && !solucion) {		
			Nodo nodo= frontera.poll();
					
			//Comprobamos si hemos llegado al objetivo 
			String fc="("+nodo.getEstado().getFila()+","+nodo.getEstado().getColumna()+")";
			if(objetive.equals(fc)) {
				visitados.add(nodo.getEstado());
				aSolucion.add(nodo);
				solucion=true;
				for(Celda c : visitados) {
					c.setColor(Color.GREEN);
				}
				mostrarCamino(aSolucion, laberinto);
			}else if(!visitados.contains(nodo.getEstado()) && nodo.getProfundidad()<1000000){
					aSolucion.add(nodo);
					visitados.add(nodo.getEstado());
					funcionSucesores(nodo.getEstado(), laberinto);
				for(int i=0;i<nodo.getEstado().getSucesores().length;i++) {
					try {
						Sucesor s1=nodo.getEstado().getSucesor(i);
						int heuristica= Math.abs(s1.getCelda().getFila() - fObjetivo) + Math.abs(s1.getCelda().getColumna() - cObjetivo);
						Nodo n = new Nodo(++id,nodo.getCosto()+s1.getCelda().getValor()+1, s1.getCelda(), nodo.getId(), s1.getMov(), nodo.getProfundidad()+1, heuristica, s1.getCostoMov()+nodo.getValor());			
						frontera.add(n);
						n.getEstado().setColor(Color.BLUE);
					}catch(NullPointerException e) {}
				}		
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
		System.out.println("\nSOLUCI�N:");
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
	* Funci�n: Mostramos la solucion y llamamos a la clase encargada de generar el .TXT
	*/
	public void mostrarSolucion(ArrayList<Nodo> aSolucion, Celda[][] laberinto) {	
		System.out.println("\n\u001B[32mSe ha alcanzado el nodo objetivo");
		GeneradorTXT gt=new GeneradorTXT();
		gt.generarTXT(laberinto,"BREADTH",aSolucion);
	}
	
	/* Nombre: funcionSucesores
	 * Tipo: Metodo
	 * Funci�n: Generar estados sucesores de cada una de los estados (celdas) del laberinto (dependiendo de muros)
	 */
	public void funcionSucesores(Celda celda, Celda[][] laberinto) {
		for(int m=0; m<celda.getMuros().length; m++) {
			if(celda.getMuro(m)==true && m==0) {
				Sucesor sucesor = new Sucesor("N",laberinto[celda.getFila()-1][celda.getColumna()], 1);
				celda.setSucesores(0, sucesor);
			}
			if(celda.getMuro(m)==true && m==1) {
				Sucesor sucesor = new Sucesor("E",laberinto[celda.getFila()][celda.getColumna()+1], 1);
				celda.setSucesores(1, sucesor);
			}
			if(celda.getMuro(m)==true && m==2) {
				Sucesor sucesor = new Sucesor("S",laberinto[celda.getFila()+1][celda.getColumna()], 1);
				celda.setSucesores(2, sucesor);
			}
			if(celda.getMuro(m)==true && m==3) {
				Sucesor sucesor = new Sucesor("O",laberinto[celda.getFila()][celda.getColumna()-1], 1);
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
			generadorPNG.generar(laberinto, "solution_"+laberinto.length+"x"+laberinto[0].length+"_BREATH_20.png");
		}
	}

}
