package car.tp3.presentation;

import java.util.Scanner;

public class Launcher {
	
	/**
	 * Lance un syst�me d'acteur et attends de recevoir des commandes dans la console.
	 * Quitte l'application si l'utilisateur envoie la commande "quit".
	 * 
	 * @param args inutilis�
	 */
	public static void main(String[] args) {
		Client client = new Client();
		if(client.start()){
			Scanner sc = new Scanner(System.in);
			System.out.print("> ");
			String line = "";
			while(!(line = sc.nextLine()).equals("quit")){
				System.out.println(client.interprete(line));
				System.out.print("> ");
			}
			client.shutdown();
			sc.close();
		}
	}

}
