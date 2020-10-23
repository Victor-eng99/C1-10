package dominio;

/* Nombre: GeneradorNodos
 * Tipo: Clase
 * Funcion: Clase encargada de generar nodos aleatorios y añadirlos a la frontera
 */
public class GenerarNodos {
	
	/* Nombre: nodosAleatorios
	 * Tipo: Metodo
	 * Funcion: Crear 10 nodos aleatorios
	 */
	public void nodosAleatorios(String initial, String objective, Celda[][] laberinto ) {
		Frontera frontera = new Frontera();
		int id=0;
		int profundidad=0;
		
		//Generamos los sucesores de cada celda del laberinto (e insertamos nodo inicial en la frontera)
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				String estado = "("+laberinto[f][c].getFila()+","+laberinto[f][c].getColumna()+")";
				id=0;
				
				if(estado.equals(initial)) {
					// Instanciamos el primer nodo (inicial) y lo insertamos en la frontera
					Nodo n = new Nodo(id, 0, estado, -1, "-", profundidad, 10, laberinto[f][c].getValor());
					frontera.offer(n);
					System.out.println("Insertado nodo inicial: " + n.toString());
					
					generarSucesores(laberinto[f][c], frontera, laberinto);
				}
				else {
					generarSucesores(laberinto[f][c], frontera, laberinto);
				}
			}
		}
		
		generarNodos(id, profundidad, laberinto, frontera);
		
		System.exit(0);
		
	}
	
	/* Nombre: generarNodos
	 * Tipo: Método
	 * Función: Generar nodos a partir de estados sucesores (simplemente generar, idPadre y profundidad son 0 para esta entrega)
	 */
	public void generarNodos(int id, int profundidad, Celda[][] laberinto, Frontera frontera) {
		for(int f=0; f<laberinto.length; f++) {
			for(int c=0; c<laberinto[0].length; c++) {
				for(int s=0; s<laberinto[f][c].getSucesores().length; s++) { // Array de sucesores de cada estado. Cada nodo que los contenga debe tener la misma profundidad
					if(laberinto[f][c].getSucesor(s)!=null) {
						int idPadre=0;
						id++;
						String estado = "("+laberinto[f][c].getSucesor(s).getCelda().getFila()+","+laberinto[f][c].getSucesor(s).getCelda().getColumna()+")";
						Nodo n = new Nodo(id, 1, estado, idPadre, laberinto[f][c].getSucesor(s).getMov(), profundidad, 10, laberinto[f][c].getSucesor(s).getCelda().getValor());
						frontera.offer(n);
						System.out.println("\nInsertado nodo: "+ n.toString());
					}
				}
			}
			System.exit(0);
		}
	}
	
	/* Nombre: generarSucesores
	 * Tipo: Método
	 * Función: Generar estados sucesores de cada una de los estados (celdas) del laberinto
	 */
	public void generarSucesores(Celda celda, Frontera frontera, Celda[][] laberinto) {
		System.out.println("\nESTADO ("+celda.getFila()+","+celda.getColumna()+")");
		System.out.println("SUCESORES:");
		for(int m=0; m<celda.getMuros().length; m++) {
			if(celda.getMuro(m)==true && m==0) {
				Sucesor sucesor = new Sucesor("N",laberinto[celda.getFila()-1][celda.getColumna()], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(0, sucesor);
			}
			else if(celda.getMuro(m)==true && m==1) {
				Sucesor sucesor = new Sucesor("E",laberinto[celda.getFila()][celda.getColumna()+1], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(1, sucesor);
			}
			else if(celda.getMuro(m)==true && m==2) {
				Sucesor sucesor = new Sucesor("S",laberinto[celda.getFila()+1][celda.getColumna()], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(2, sucesor);
			}
			else if(celda.getMuro(m)==true && m==3) {
				Sucesor sucesor = new Sucesor("O",laberinto[celda.getFila()][celda.getColumna()-1], 1);
				System.out.println(sucesor.toString());
				celda.setSucesores(3, sucesor);
			}
		}
	} 

}
