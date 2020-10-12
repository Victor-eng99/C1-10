package presentacion;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import dominio.Celda;

/* Nombre: GeneradorPNG
 * Tipo: Clase
 * Funcion: Generar imagen PNG a partir de archivo JSON
 */
public class GeneradorPNG {
	
	/* Nombre: GeneradorPNG
	 * Tipo: Metodo
	 * Funcion: Constructor de la clase GeneradorPNG
	 */
	public GeneradorPNG() {
		// Vacío
	}
	
	/* Nombre: generar
	 * Tipo: Metodo
	 * Funcion: Creacion y pintado de archivo PNG (similar a GeneradorFrame)
	 */
	public void generar(Celda[][] laberinto) {
        BufferedImage bufferedImage = new BufferedImage(laberinto[0].length * Celda.TAMANO+10, laberinto.length * Celda.TAMANO+30, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, laberinto[0].length * Celda.TAMANO, laberinto.length * Celda.TAMANO);
        
		for (int i = 0; i < laberinto.length; i++) { // Este bucle anidado dibujara cada una de las celdas que componen el laberinto
			for (int j = 0; j < laberinto[0].length; j++) {
				int x = j*Celda.TAMANO; // La variable x representara la coordenada en eje de abcisas
				int y = i*Celda.TAMANO; // La variable y representara la coordenada en eje de ordenadas
				
				g2.setColor(Color.WHITE);
				g2.fillRect(x, y, Celda.TAMANO, Celda.TAMANO); // Rellena un recangulo con las medidas de la clase Celda
				
				g2.setColor(Color.BLACK);
				if(!laberinto[i][j].getMuro(0)) {
					g2.drawLine(x, y, x+Celda.TAMANO, y); // Muro superior (N)
				}
				if(!laberinto[i][j].getMuro(1)) {
					g2.drawLine(x+Celda.TAMANO, y, x+Celda.TAMANO, y+Celda.TAMANO); // Muro derecha (E)
				}
				if(!laberinto[i][j].getMuro(2)) {
					g2.drawLine(x, y+Celda.TAMANO, x+Celda.TAMANO, y+Celda.TAMANO); // Muro inferior (S)
				}
				if(!laberinto[i][j].getMuro(3)) {
					g2.drawLine(x, y, x, y+Celda.TAMANO); // Muro izquierda (O)
				}
			}
		}
        
        g2.dispose();
        
        try {
            ImageIO.write(bufferedImage, "png", new File("laberinto_"+laberinto.length+"x"+laberinto[0].length+".png"));
        } catch (IOException ex) {
           
        }

	}

}
