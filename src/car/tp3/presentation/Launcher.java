package car.tp3.presentation;

import java.util.Scanner;

public class Launcher {
	
	/**
	 * Lance un système d'acteur et attends de recevoir des commandes dans la console.
	 * Quitte l'application si l'utilisateur envoie la commande "quit".
	 * 
	 * @param args inutilisé
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez entrer le nom du serveur : ");
		String name = sc.nextLine();
		Client client = new Client(name);
		if(client.start()){
			System.out.print("> ");
			String line = "";
			while(!(line = sc.nextLine()).equals("quit")){
				System.out.println(client.interprete(line));
				System.out.print("> ");
			}
			client.shutdown();
		}
		sc.close();
	}

}
