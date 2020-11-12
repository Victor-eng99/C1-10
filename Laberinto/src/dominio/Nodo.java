package dominio;


/* Nombre: Nodo
* Tipo: Clase
* Funcion: Construccion de los atributos de la clase Nodo
*/

public class Nodo implements Comparable<Nodo>{
	private int id;
	private Celda estado;
	private int valor;
	private int costo;
	private String accion;
	private int idPadre;
	private int profundidad;
	private int heuristica;
	
	
	/* Nombre: Nodo
	 * Tipo: Metodo
	 * Funcion: Constructor de clase Nodo
	 */
	public Nodo(int id, int costo, Celda estado, int idPadre, String accion, int profundidad, int heuristica, int valor) {
		this.id = id;
		this.estado = estado;
		this.valor = valor;
		this.costo = costo;
		this.accion = accion;
		this.idPadre = idPadre;
		this.profundidad = profundidad;
		this.heuristica = heuristica;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Celda getEstado() {
		return estado;
	}

	public void setEstado(Celda estado) {
		this.estado = estado;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public int getIdPadre() {
		return idPadre;
	}

	public void setIdPadre(int idPadre) {
		this.idPadre = idPadre;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public int getHeuristica() {
		return heuristica;
	}

	public void setHeuristica(int heuristica) {
		this.heuristica = heuristica;
	}
	
	@Override
	public String toString() {
		return "["+this.id+"]["+this.costo+",("+this.estado.getFila()+","+this.estado.getColumna()+"),"+this.idPadre+","+this.accion+","+this.profundidad+","+this.heuristica+","+this.valor+"]";
	}


	@Override
	public int compareTo(Nodo o) {
		if (valor < o.valor) {
            return -1;
        }
        if (valor > o.valor) {
            return 1;
        }
		return 0;
	}


}
