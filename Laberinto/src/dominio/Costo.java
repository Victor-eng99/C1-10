package dominio;

import java.util.ArrayList;
import java.util.Collections;

public class Costo {
	
	ArrayList<Nodo> frontera = new ArrayList<Nodo>();
	
	public void nodoInicial(String initial, String objetive, Celda[][] laberinto) {
		int id=0;
		int profundidad=0;
		
		//Sacamos los valores de la fila/columna del objetivo para obtener la heuristica de Manhattan
		String cadena= objetive.substring(objetive.indexOf("(")+1,objetive.indexOf(")"));
		String[] s= cadena.split(",");
		int fObjetivo=Integer.parseInt(s[0]);
		int cObjetivo= Integer.parseInt(s[1]);
		
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				String fc="("+laberinto[f][c].getFila()+","+laberinto[f][c].getColumna()+")";
				if(initial.equals(fc)) {
					int heuristica= Math.abs(laberinto[f][c].getFila() - fObjetivo) + Math.abs(laberinto[f][c].getColumna() - cObjetivo);
				    Nodo n = new Nodo(id, 0, laberinto[f][c], -1, "-", profundidad, heuristica, 0);			
				    frontera.add(n); 
				    System.out.println("Insertado nodo " + n.toString());
					costo(objetive,laberinto,fObjetivo,cObjetivo);
				}
			}
		}
	}
	
	/* Nombre: costo
	 * Tipo: Metodo
	 * Funcion: Implementacion de algoritmo principal de busqueda por costo uniforme
	 */
	public void costo(String objetive, Celda[][] laberinto,int fObjetivo,int cObjetivo) {
		ArrayList<Celda> visitados=new ArrayList<Celda>();
		ArrayList<Nodo> nodosVisitados=new ArrayList<Nodo>();
		boolean solucion=false;
		int id=0;
		
		while(!solucion && !frontera.isEmpty()) {
			System.out.println("\n____________________________");
			Nodo padre = frontera.get(0);
			frontera.remove(0);
			System.out.println("Sacado nodo " + padre.toString());
			String fc="("+padre.getEstado().getFila()+","+padre.getEstado().getColumna()+")";
			if(objetive.equals(fc)) {			
				solucion=true; // Hemos alcanzado el objetivo
				visitados.add(padre.getEstado());
				nodosVisitados.add(padre);
				mostrarCamino(nodosVisitados, laberinto);
			} else {
				if(!visitados.contains(padre.getEstado())) {
					funcionSucesores(padre.getEstado(), laberinto);
					for(int i=0; i<padre.getEstado().getSucesores().length; i++) {
						try {
							Sucesor s=padre.getEstado().getSucesor(i);
							int heuristica= Math.abs(s.getCelda().getFila() - fObjetivo) + Math.abs(s.getCelda().getColumna() - cObjetivo);
							Nodo n = new Nodo(++id, s.getCostoMov()+padre.getCosto(), s.getCelda(), padre.getId(), s.getMov(), padre.getProfundidad()+1, heuristica, s.getCostoMov()+padre.getCosto());
							frontera.add(n);
							System.out.println("Insertado nodo " + n.toString());
						}catch(NullPointerException e) {}
					}
				} else {
					System.out.println("CUT en estado ("+padre.getEstado().getFila()+","+padre.getEstado().getColumna()+")");
				}
				visitados.add(padre.getEstado()); // Nodo expandido = Su estado ha sido visitado
				nodosVisitados.add(padre);
				Collections.sort(frontera);
				System.out.println("\n");
				segundosCriterios();
				System.out.println("");
				System.out.println("\nFrontera:");
				for(Nodo n:frontera) {
					System.out.println(n.toString());
				}
	
			}

		}
		
	}
	
	/* Nombre: segundosCriterios
	 * Tipo: Metodo
	 * Funcion: Establecer en cabecera de frontera el nodo indicado segun los criterios de seleccion:
	 * 1º valor, 2º fila, 3º columna
	 */
	public void segundosCriterios() {
		ArrayList<Nodo> valorMinimo = new ArrayList<Nodo>();
		ArrayList<Nodo> filaMinima = new ArrayList<Nodo>();
		ArrayList<Integer> columnas = new ArrayList<Integer>();
		ArrayList<Integer> filas = new ArrayList<Integer>();
		int valor;

		try {
			System.out.println("Nodos con minimo valor de frontera:");
			valor=frontera.get(0).getValor();
			for(Nodo n:frontera) {
				if(n.getValor()==valor) {
					valorMinimo.add(n);
					filas.add(n.getEstado().getFila());
					System.out.print(n.toString()+" ");
				}
			}
			
			int fMin = Collections.min(filas);
			
			System.out.println("\n");
			System.out.println("Nodos con fila minima:");
			for(Nodo n:valorMinimo) {
				if(n.getEstado().getFila()==fMin) {				
					filaMinima.add(n);
					columnas.add(n.getEstado().getColumna());
					System.out.print(n.toString()+" ");
				}
			}
			
			int cMin = Collections.min(columnas);
			System.out.println("\n");
			System.out.println("Columna minima: " + cMin);
			
			for(Nodo n:filaMinima) {
				if(n.getEstado().getColumna()==cMin) {
					System.out.println("\nNodo a cabecera de frontera: " + n.toString());
					frontera.remove(n);
					frontera.add(0, n);
				}
			}
			
		} catch(IndexOutOfBoundsException e) {}
		
	}
	
	public void mostrarCamino(ArrayList<Nodo> nodosVisitados, Celda[][] laberinto) {
		ArrayList<Nodo> sol = new ArrayList<Nodo>();
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		Nodo siguiente = nodosVisitados.get(nodosVisitados.size()-1);
		while(siguiente.getIdPadre()!=-1) {
			for(int v=0; v<nodosVisitados.size(); v++) {
				if(siguiente.getIdPadre()==nodosVisitados.get(v).getId()) {
					Nodo padre = nodosVisitados.get(v);
					sol.add(padre);
					siguiente = padre;
				}
			}
		}
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
		gt.generarTXT(laberinto,"UNIFORM",aSolucion);
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

}
