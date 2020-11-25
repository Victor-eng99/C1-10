package presentacion;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import dominio.GeneradorLaberinto;

public class InterfazPrincipal {
	private static Scanner teclado = new Scanner(System.in);
	static GeneradorLaberinto generadorLaberinto;
	static LecturaFichero lecturaFichero;

	public static void main(String[] args) throws IOException {
		
		System.out.println("\033[34mGENERADOR DE LABERINTOS");
		
		while(true) {
			menu();
		}
		
	}
	
	public static void menu() throws IOException {
		int opcion = -1;
		do {
			System.out.println("\n\033[34mMenu:\u001B[0m");
			System.out.println("1. Generar laberinto a partir de parámetros. ");
			System.out.println("2. Leer fichero JSON y generar laberinto. ");
			System.out.println("3. Leer fichero JSON y elegir tipo de Busqueda ");
			System.out.println("0. Salir");
			System.out.println("\n\u001B[36mSeleccione una opción: \u001B[0m");
			try {
				opcion = teclado.nextInt();
			} catch(InputMismatchException e) {
				System.err.println("Introduzca un valor numérico entre 0 y 2");
				teclado.next();
			}
		} while(opcion>3 || opcion<0);
		
		comenzar(opcion);
	}
	
	public static void comenzar(int opcion) throws IOException {
		if(opcion==1) {
			generadorLaberinto = new GeneradorLaberinto();
			generadorLaberinto.run();
		}
		else if(opcion==2) {
			lecturaFichero = new LecturaFichero();
			lecturaFichero.obtener(0,0);
		}
		else if(opcion==3) {
			do {
				System.out.println("\n\033[34mSeleccione una estrategia de busqueda:\u001B[0m");
				System.out.println();
				System.out.println("0.  Busqueda en Anchura ");
				System.out.println("1.  Busqueda en A*");
				System.out.println("2.  Busqueda en Costo Uniforme");	
				System.out.println("3.  Busqueda en Profundidad");	
				System.out.println("4.  Busqueda Voraz");
				System.out.println("5.  Volver atras");
				System.out.println("\n\u001B[36mSeleccione una opción: \u001B[0m");
				try {
					opcion = teclado.nextInt();
				} catch(InputMismatchException e) {
					System.err.println("Introduzca un valor numérico entre 0 y 3");
					teclado.next();
				}
			}while(opcion>5|| opcion<0);
			if(opcion==5)
				menu();
			lecturaFichero = new LecturaFichero();
			lecturaFichero.obtener(1,opcion);
		}
		else {
			System.out.println("\n\n\033[34mHasta pronto");
			System.exit(0);
		}
	}

}
