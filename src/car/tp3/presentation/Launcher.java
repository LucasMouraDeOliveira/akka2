package car.tp3.presentation;

import java.io.IOException;
import java.util.Scanner;

public class Launcher {
	
	/**
	 * Lance un système d'acteur et attends de recevoir des commandes dans la console.
	 * Quitte l'application si l'utilisateur envoie la commande "quit".
	 * 
	 * @param args inutilisé
	 */
	public static void main(String[] args) {
		Client client = new Client();
		if(client.start()){
			if(args.length == 1){
				try {
					new GraphBuilder(args[0]).build(client);
				} catch (IOException e) {
				}
			}
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
