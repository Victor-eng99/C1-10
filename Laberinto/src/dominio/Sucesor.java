package dominio;


/* Nombre: Camino
* Tipo: Clase
* Funcion: Construccion de la clase Sucesores
*/

public class Sucesor {
	
	private String mov;
	private Celda c1;
	private int costo_mov;
	
	/* Nombre: Camino
	 * Tipo: Metodo
	 * Funcion: Constructor de clase Camino
	 */
	public Sucesor(String mov, Celda c1, int costo_mov) {
		super();
		this.mov = mov;
		this.c1 = c1;
		this.costo_mov = costo_mov;
	}

	
	public String getMov() {
		return mov;
	}

	public void setMov(String mov) {
		this.mov = mov;
	}

	public Celda getC1() {
		return c1;
	}


	public void setC1(Celda c1) {
		this.c1 = c1;
	}

	public int getCosto_mov() {
		return costo_mov;
	}

	public void setCosto_mov(int costo_mov) {
		this.costo_mov = costo_mov;
	}
	
	
}
