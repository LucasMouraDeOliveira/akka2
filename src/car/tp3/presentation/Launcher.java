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
		int port = -1;
		while(port == -1){
			System.out.println("Veuillez entrer le port du serveur : ");
			port = getPort(sc);
		}
		System.out.println("Veuillez entrer le nom du serveur : ");
		String name = sc.nextLine();
		Server server = new Server(port, name);
		System.out.print("> ");
		String line = "";
		while(!(line = sc.nextLine()).equals("quit")){
			System.out.println(server.interprete(line));
			System.out.println("> ");
		}
		sc.close();
		server.shutdown();
	}
	
	/**
	 * Renvoie un numéro de port lu sur la console s'il est valide (entre 1025 et 65536) ou -1.
	 * Affiche également un message d'erreur en cas de port invalide
	 * 
	 * @param sc le scanner
	 * 
	 * @return le numéro du port, -1 s'il est invalide
	 */
	private static int getPort(Scanner sc) {
		String line = sc.nextLine();
		int value;
		try{
			value = Integer.valueOf(line);
			if(value > 65536 || value <= 1024){
				System.out.println("Port invalide, choisissez en un entre 1025 et 65536");
				return -1;
			}
		} catch(NumberFormatException e) {
			System.out.println("Port invalide, essayez plutôt un nombre");
			return -1;
		}
		return value;
	}

}
