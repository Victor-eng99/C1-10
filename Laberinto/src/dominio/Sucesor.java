package dominio;


/* Nombre: Camino
* Tipo: Clase
* Funcion: Construccion de la clase Sucesores
*/

public class Sucesor {
	
	private String mov;
	private Celda celda;
	private int costoMov;
	
	/* Nombre: Camino
	 * Tipo: Metodo
	 * Funcion: Constructor de clase Camino
	 */
	public Sucesor(String mov, Celda celda, int costoMov) {
		this.mov = mov;
		this.celda = celda;
		this.costoMov = costoMov;
	}

	
	public String getMov() {
		return mov;
	}

	public void setMov(String mov) {
		this.mov = mov;
	}

	public Celda getCelda() {
		return celda;
	}


	public void setC1(Celda celda) {
		this.celda = celda;
	}

	public int getCostoMov() {
		return costoMov;
	}

	public void setCostoMov(int costoMov) {
		this.costoMov = costoMov;
	}
	
	@Override
	public String toString() {
		return "["+this.mov+",("+this.celda.getFila()+","+this.celda.getColumna()+"),"+this.costoMov+"]";
	}
	
}
