package car.tp3.presentation;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;

/**
 * Serveur Akka pour la recette du TP3. Le serveur contient un actorSystem lanc�
 * sur un port pass� en param�tre. Le serveur est un simple conteneur d'acteurs
 * pour la pr�sentation du TP.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class Server {

	protected ActorSystem actorSystem;

	/**
	 * Cr�e un serveur sur le port et avec le nom sp�cifi�s. Le port vient
	 * �craser celui d�fini dans le fichier de configuration (0 de base).
	 * 
	 * @param port
	 *            le port du serveur
	 * @param name
	 *            le nom du serveur
	 */
	public Server(int port, String name) {
		this.createActorSystem(port, name);
	}

	private void createActorSystem(int port, String name) throws IllegalArgumentException {
		Config config = ConfigFactory.load();
		Config portConfig = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port);
		this.actorSystem = ActorSystem.create(name, portConfig.withFallback(config));
	}

	/**
	 * Lance un serveur Akka.
	 * 
	 * @param args
	 *            les arguments obligatoires pour le lancement du serveur :
	 *            num�ro de port et nom
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Veuillez renseigner un port et un nom de serveur");
		} else {
			try {
				int port = Integer.valueOf(args[0]);
				String name = args[1];
				if (name.isEmpty()) {
					System.out.println("Veuillez renseigner un nom de serveur");
				} else {
					new Server(port, name);
				}
			} catch (NumberFormatException e) {
				System.out.println("Veuillez renseigner un num�ro de port");
			}
		}
	}

}
