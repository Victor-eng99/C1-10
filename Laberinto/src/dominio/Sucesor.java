package dominio;


/* Nombre: Camino
* Tipo: Clase
* Funcion: Construccion de la clase Sucesores
*/

public class Sucesor {
	
	private String mov;
	private int[] estado;
	private int costo_mov;
	
	/* Nombre: Camino
	 * Tipo: Metodo
	 * Funcion: Constructor de clase Camino
	 */
	public Sucesor(String mov, int[] estado, int costo_mov) {
		super();
		this.mov = mov;
		this.estado = estado;
		this.costo_mov = costo_mov;
	}

	
	public String getMov() {
		return mov;
	}

	public void setMov(String mov) {
		this.mov = mov;
	}

	public int[] getEstado() {
		return estado;
	}

	public void setEstado(int[] estado) {
		this.estado = estado;
	}

	public int getCosto_mov() {
		return costo_mov;
	}

	public void setCosto_mov(int costo_mov) {
		this.costo_mov = costo_mov;
	}
	
	
}
