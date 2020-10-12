package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dominio.Celda;

/* Nombre: GeneradorFrame
 * Tipo: Clase
 * Funcion: Generacion y modificación de JFrame contenedor del laberinto
 */
public class GeneradorFrame {
	JFrame frameLaberinto; // Variable que representa el JFrame contenedor del laberinto
	Celda[][] aux; // La variable aux almacenara el array que representa el laberinto y sera usada como ayuda para pintar las celdas
	AuxiliarPanel panel;
	
	/* Nombre: GeneradorFrame
	 * Tipo: Metodo
	 * Funcion: Constructor de clase GeneradorFrame
	 */
	public GeneradorFrame() {
		// Vacio
	}
	
	/* Nombre: generar
	 * Tipo: Metodo
	 * Funcion Crear JFrame que alojara el laberinto
	 */
	public void generar(Celda[][] laberinto) {
		frameLaberinto = new JFrame("Laberinto " + laberinto.length + "x" + laberinto[0].length);
		aux = laberinto; 

		frameLaberinto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new AuxiliarPanel(); 
		frameLaberinto.getContentPane().add(BorderLayout.CENTER, panel);

		frameLaberinto.setSize(laberinto[0].length * Celda.TAMANO + 10, laberinto.length * Celda.TAMANO + 30);
		frameLaberinto.setBackground(Color.BLACK);
		frameLaberinto.setLocationRelativeTo(null);
		frameLaberinto.setResizable(false);
		frameLaberinto.setVisible(true);
		
	}
	
	/* Nombre: repintar
	 * Tipo: Metodo
	 * Funcion: Actualizar el JFrame que representa el laberinto
	 */
	public void repintar() {
		frameLaberinto.repaint();
	}
	
	/* Nombre: AuxiliarPanel
	 * Tipo: Clase
	 * Funcion: Clase implementadora de Graphics, la cual pintara todas las celdas dentro de panel con su correspondiente color
	 */
	class AuxiliarPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		
		@Override
		public void paintComponent(Graphics g) {

			for (int i = 0; i < aux.length; i++) { // Este bucle anidado dibujara cada una de las celdas que componen el laberinto
				for (int j = 0; j < aux[0].length; j++) {
					int x = j*Celda.TAMANO; // La variable x representara la coordenada en eje de abcisas
					int y = i*Celda.TAMANO; // La variable y representara la coordenada en eje de ordenadas
					
					g.setColor(aux[i][j].getColor());
					g.fillRect(x, y, Celda.TAMANO, Celda.TAMANO); // Rellena un recangulo con las medidas de la clase Celda
					
					g.setColor(Color.BLACK);
					if(!aux[i][j].getMuro(0)) {
						g.drawLine(x, y, x+Celda.TAMANO, y); // Muro superior (N)
					}
					if(!aux[i][j].getMuro(1)) {
						g.drawLine(x+Celda.TAMANO, y, x+Celda.TAMANO, y+Celda.TAMANO); // Muro derecha (E)
					}
					if(!aux[i][j].getMuro(2)) {
						g.drawLine(x, y+Celda.TAMANO, x+Celda.TAMANO, y+Celda.TAMANO); // Muro inferior (S)
					}
					if(!aux[i][j].getMuro(3)) {
						g.drawLine(x, y, x, y+Celda.TAMANO); // Muro izquierda (O)
					}
				}
			}
			
		}
	}

}
