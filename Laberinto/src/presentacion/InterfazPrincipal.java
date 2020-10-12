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
		int opcion;
		
		System.out.println("GENERADOR DE LABERINTOS");
		
		while(true) {
			opcion = menu();
			comenzar(opcion);
		}
		
	}
	
	public static int menu() {
		int opcion = -1;
		
		do {
			System.out.println("\nMenú:");
			System.out.println("1. Generar laberinto a partir de parámetros. ");
			System.out.println("2. Leer fichero JSON y generar laberinto. ");
			System.out.println("0. Salir");
			System.out.println("\nSeleccione una opción: ");
			try {
				opcion = teclado.nextInt();
			} catch(InputMismatchException e) {
				System.err.println("Introduzca un valor numérico entre 0 y 2");
				teclado.next();
			}
		} while(opcion>2 || opcion<0);
		
		return opcion;
	}
	
	public static void comenzar(int opcion) throws IOException {
		if(opcion==1) {
			generadorLaberinto = new GeneradorLaberinto();
			generadorLaberinto.run();
		}
		else if(opcion==2) {
			lecturaFichero = new LecturaFichero();
			lecturaFichero.obtener();
		}
		else {
			System.out.println("\nHasta pronto");
			System.exit(0);
		}
	}

}
