package dominio;


/* Nombre: Nodo
* Tipo: Clase
* Funcion: Construccion de los atributos de la clase Nodo
*/

public class Nodo {
	private int id;
	private int estado;
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
	public Nodo(int id, int estado, int valor, int costo, String accion, int idPadre, int profundidad, int heuristica) {
		super();
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

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
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


}
