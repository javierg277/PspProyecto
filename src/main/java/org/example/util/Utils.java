package org.example.util;

import java.util.Scanner;

public class Utils {


	public static int intInput(String msg) {
		Scanner sc = new Scanner(System.in);
		
		boolean isCorrect = false;
		int number = 0;
		
		do {
			System.out.print(msg);
			try {
				number = sc.nextInt();
				isCorrect = true;
			}catch(Exception InputMismatchException){
				System.out.println("");
				System.out.println("Entrada incorrecta.");
				System.out.println("");
				sc.nextLine();
			}
		}while(!isCorrect);
		
		return number;
	}

	public static double doubleInput(String msg) {
		Scanner sc = new Scanner(System.in);

		boolean isCorrect = false;
		double number = 0;

		do {
			System.out.print(msg);
			try {
				number = sc.nextDouble();
				isCorrect = true;
			}catch(Exception InputMismatchException){
				System.out.println("");
				System.out.println("Entrada incorrecta.");
				System.out.println("");
				sc.nextLine();
			}
		}while(!isCorrect);

		return number;
	}
	
	public static String stringInput(String msg) {
		System.out.print(msg);
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
	
	public static void showMessage(String msg) {
		System.out.println(msg);
		
	}
}