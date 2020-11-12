package dominio;

import java.util.ArrayList;
import java.util.Collections;

public class Costo {
	
	ArrayList<Nodo> frontera = new ArrayList<Nodo>();
	
	public void nodoInicial(String initial, String objetive, Celda[][] laberinto) {
		int id=0;
		int profundidad=0;
		
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				String fc="("+laberinto[f][c].getFila()+","+laberinto[f][c].getColumna()+")";
				if(initial.equals(fc)) {
				    Nodo n = new Nodo(id, 0, laberinto[f][c], -1, "-", profundidad, 10, 0);			
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
				mostrarCamino(nodosVisitados);
				System.out.println("\nSolucion encontrada");
				System.out.println("Estado solución: " + fc);
			} else {
				funcionSucesores(padre.getEstado(), laberinto);
				for(int i=0; i<padre.getEstado().getSucesores().length; i++) {
					try {
						Sucesor s=padre.getEstado().getSucesor(i);

						if(!visitados.contains(s.getCelda())) {
							Nodo n = new Nodo(++id, s.getCostoMov()+padre.getCosto(), s.getCelda(), padre.getId(), s.getMov(), padre.getProfundidad()+1, 10, s.getCostoMov()+padre.getCosto());			
							frontera.add(n);
							System.out.println("Insertado nodo " + n.toString());
							System.out.println("Tamano de frontera = "+frontera.size());
						} else {
							System.out.println("CUT en estado ("+s.getCelda().getFila()+","+s.getCelda().getColumna()+")");
						}
					}catch(NullPointerException e) {}
				}
				visitados.add(padre.getEstado()); // Nodo expandido = Su estado ha sido visitado
				nodosVisitados.add(padre);
				Collections.sort(frontera);
				System.out.println("En cabecera se encuentra el nodo con estado ("+frontera.get(0).getEstado().getFila()+","+frontera.get(0).getEstado().getColumna()+") y valor " + frontera.get(0).getValor());
			}

		}
		
	}
	
	public void mostrarCamino(ArrayList<Nodo> nodosVisitados) {
		System.out.println("\nSOLUCIÓN INVERSA:");
		Nodo siguiente = nodosVisitados.get(nodosVisitados.size()-1);
		System.out.println(siguiente.toString());
		while(siguiente.getIdPadre()!=-1) {
			for(int v=0; v<nodosVisitados.size(); v++) {
				if(siguiente.getIdPadre()==nodosVisitados.get(v).getId()) {
					Nodo padre = nodosVisitados.get(v);
					System.out.println(padre.toString());
					siguiente = padre;
				}
			}
		}
	}
	
	/* Nombre: funcionSucesores
	 * Tipo: Método
	 * Función: Generar estados sucesores de cada una de los estados (celdas) del laberinto (dependiendo de muros)
	 */
	public void funcionSucesores(Celda celda, Celda[][] laberinto) {
		for(int m=0; m<celda.getMuros().length; m++) {
			if(celda.getMuro(m)==true && m==0) {
				Sucesor sucesor = new Sucesor("N",laberinto[celda.getFila()-1][celda.getColumna()], laberinto[celda.getFila()-1][celda.getColumna()].getValor());
				celda.setSucesores(0, sucesor);
			}
			if(celda.getMuro(m)==true && m==1) {
				Sucesor sucesor = new Sucesor("E",laberinto[celda.getFila()][celda.getColumna()+1], laberinto[celda.getFila()][celda.getColumna()+1].getValor());
				celda.setSucesores(1, sucesor);
			}
			if(celda.getMuro(m)==true && m==2) {
				Sucesor sucesor = new Sucesor("S",laberinto[celda.getFila()+1][celda.getColumna()], laberinto[celda.getFila()+1][celda.getColumna()].getValor());
				celda.setSucesores(2, sucesor);
			}
			if(celda.getMuro(m)==true && m==3) {
				Sucesor sucesor = new Sucesor("O",laberinto[celda.getFila()][celda.getColumna()-1], laberinto[celda.getFila()][celda.getColumna()-1].getValor());
				celda.setSucesores(3, sucesor);
			}
		}
	} 

}
