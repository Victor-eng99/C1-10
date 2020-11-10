package dominio;

import java.util.ArrayList;

public class Costo {
	
	Frontera frontera = new Frontera();
	
	public void nodoInicial(String initial, String objetive, Celda[][] laberinto) {
		int id=0;
		int profundidad=0;
		
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				String fc="("+laberinto[f][c].getFila()+","+laberinto[f][c].getColumna()+")";
				if(initial.equals(fc)) {
				    Nodo n = new Nodo(id, laberinto[f][c].getValor(), laberinto[f][c], -1, "-", profundidad, 10, laberinto[f][c].getValor());
					frontera.offer(n);
					costo(n,objetive,laberinto);
				}
			}
		}
	}
	
	public void costo(Nodo padre, String objetive, Celda[][] laberinto) {
		int id=padre.getId();
		ArrayList<Celda> visitados=new ArrayList<Celda>();
		boolean solucion=false;
		
		while(!solucion && frontera.isEmpty()) {
			visitados.add(frontera.poll().getEstado());
			
			String fc="("+visitados.get(visitados.size()-1).getFila()+","+visitados.get(visitados.size()-1).getColumna()+")";
			if(objetive.equals(fc)) {			
				solucion=true;
			}
			
			int sucesores = funcionSucesores(padre.getEstado(), laberinto);
			for(int i=sucesores-1;i>=0;i--) {
				try {
					Sucesor s=padre.getEstado().getSucesor(i);
					Nodo n = new Nodo(++id, padre.getEstado().getSucesor(i).getCostoMov(), s.getCelda(), padre.getId(), s.getMov(), padre.getProfundidad()+1, 10, s.getCelda().getValor());			
					n.toString();
					frontera.offer(n);
				}catch(NullPointerException e) {}
			}
			
			frontera.ordenar();

		}
		
		
	}
	
	/* Nombre: funcionSucesores
	 * Tipo: Método
	 * Función: Generar estados sucesores de cada una de los estados (celdas) del laberinto (dependiendo de muros)
	 */
	public int funcionSucesores(Celda celda, Celda[][] laberinto) {
		int sucesores=0;
		System.out.println("\nESTADO ("+celda.getFila()+","+celda.getColumna()+")");
		System.out.println("SUCESORES:");
		for(int m=0; m<celda.getMuros().length; m++) {
			if(celda.getMuro(m)==true && m==0) {
				Sucesor sucesor = new Sucesor("N",laberinto[celda.getFila()-1][celda.getColumna()], 1+laberinto[celda.getFila()-1][celda.getColumna()].getValor());
				System.out.println(sucesor.toString());
				celda.setSucesores(0, sucesor);
				sucesores++;
			}
			if(celda.getMuro(m)==true && m==1) {
				Sucesor sucesor = new Sucesor("E",laberinto[celda.getFila()][celda.getColumna()+1], 1+laberinto[celda.getFila()][celda.getColumna()].getValor());
				System.out.println(sucesor.toString());
				celda.setSucesores(1, sucesor);
				sucesores++;
			}
			if(celda.getMuro(m)==true && m==2) {
				Sucesor sucesor = new Sucesor("S",laberinto[celda.getFila()+1][celda.getColumna()], 1+laberinto[celda.getFila()+1][celda.getColumna()].getValor());
				System.out.println(sucesor.toString());
				celda.setSucesores(2, sucesor);
				sucesores++;
			}
			if(celda.getMuro(m)==true && m==3) {
				Sucesor sucesor = new Sucesor("O",laberinto[celda.getFila()][celda.getColumna()-1], 1+laberinto[celda.getFila()+1][celda.getColumna()].getValor());
				System.out.println(sucesor.toString());
				celda.setSucesores(3, sucesor);
				sucesores++;
			}
		}
		return sucesores;
	} 

}
