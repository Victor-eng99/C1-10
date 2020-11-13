package dominio;

import java.util.ArrayList;
import java.util.Collections;

public class Costo {
	
	ArrayList<Nodo> frontera = new ArrayList<Nodo>();
	
	public void nodoInicial(String initial, String objetive, Celda[][] laberinto) {
		int id=0;
		int profundidad=0;
		int fObjetivo= Integer.parseInt(objetive.substring(1, 2));
		int cObjetivo= Integer.parseInt(objetive.substring(3, 4));
		
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				String fc="("+laberinto[f][c].getFila()+","+laberinto[f][c].getColumna()+")";
				if(initial.equals(fc)) {
					int heuristica= Math.abs(laberinto[f][c].getFila() - fObjetivo) + Math.abs(laberinto[f][c].getColumna() - cObjetivo);
				    Nodo n = new Nodo(id, 0, laberinto[f][c], -1, "-", profundidad, heuristica, 0);			
				    frontera.add(n); 
				    System.out.println("Insertado nodo " + n.toString());
					costo(objetive,laberinto);
				}
			}
		}
	}
	
	public void costo(String objetive, Celda[][] laberinto) {
		ArrayList<Celda> visitados=new ArrayList<Celda>();
		ArrayList<Nodo> nodosVisitados=new ArrayList<Nodo>();
		boolean solucion=false;
		int id=0;
		
		int fObjetivo= Integer.parseInt(objetive.substring(1, 2));
		int cObjetivo= Integer.parseInt(objetive.substring(3, 4));
		
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
				System.out.println("\nSolucion encontrada");
				mostrarCamino(nodosVisitados);
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
				System.out.println("Cabecera de frontera = ("+frontera.get(0).getEstado().getFila()+","+frontera.get(0).getEstado().getColumna()+") y valor " + frontera.get(0).getValor());
	
			}

		}
		
	}
	
	public void mostrarCamino(ArrayList<Nodo> nodosVisitados) {
		ArrayList<Nodo> sol = new ArrayList<Nodo>();
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
			System.out.println(sol.get(s).toString());
		}
		System.out.println(nodosVisitados.get(nodosVisitados.size()-1));
	}
	
	/* Nombre: funcionSucesores
	 * Tipo: Método
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
