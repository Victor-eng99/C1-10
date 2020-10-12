package dominio;

import java.awt.Color; 

/* Nombre: Celda
 * Tipo: Clase
 * Funcion: Representación grafica de casillas o espacios en el array donde se
 * construira el laberinto
*/
public class Celda {
	public static final int TAMANO = 12; // Variable estatica de tamaño en pixels (alto y ancho)
	
	private int posX;
	private int posY;
	
	private int fila;
	private int columna;
	
	private int dirHastaCelda; // Variable que representa el identificador de direccion con la que se ha llegado a tomar dicha celda

	private boolean[] muros = new boolean[4]; // Array booleano donde quedaran representaos los muros (true=hay vecina, false=no hay vecina)
	
	private Color color; // Variable en representacian del color de la celda
	
	private int maxVecinos;
	
	boolean blanca;
	
	private int valor;
	
	/* Nombre: Celda
	 * Tipo: Metodo
	 * Funcion: Constructor de clase Celda
	 */
	public Celda(int x, int y) {
		posX = x*TAMANO;
		posY = y*TAMANO;
		fila = y;
		columna = x;
		color = Color.GRAY;
		for(int i=0; i < muros.length; i++) {
			muros[i] = false;
		}
		dirHastaCelda=-1;
		blanca = false;
		maxVecinos=4;
		valor = 0;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
	
	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean getMuro(int index) {
		return muros[index];
	}
	
	public boolean[] getMuros() {
		return muros;
	}

	public void setMuro(int index, boolean valor) {
		this.muros[index] = valor;
	}

	public int getDirHastaCelda() {
		return dirHastaCelda;
	}

	public void setDirHastaCelda(int dirHastaCelda) {
		this.dirHastaCelda = dirHastaCelda;
	}

	public boolean isBlanca() {
		return blanca;
	}

	public void setBlanca(boolean blanca) {
		this.blanca = blanca;
	}

	public int getMaxVecinos() {
		return maxVecinos;
	}

	public void setMaxVecinos(int maxVecinos) {
		this.maxVecinos = maxVecinos;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
}
