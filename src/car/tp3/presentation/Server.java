package car.tp3.presentation;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;

/**
 * Serveur Akka pour la recette du TP3.
 * Le serveur contient un actorSystem lancé sur un port passé en paramètre et permet de :
 * <ul>
 * 	<li>Démarrer le système</li>
 * 	<li>Arreter (shutdown) le système</li>
 * 	<li>Ajouter un acteur à ce système</li>
 * 	<li>Supprimer un acteur de ce système</li>
 * 	<li>Envoyer un message à un acteur sur n'importe quel système</li>
 * 	<li>Créer un lien (père/fils ou voisin) entre deux acteurs sur n'importe quel système</li>
 * </ul>
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class Server {
	
	protected ActorSystem actorSystem;
	
	protected int port;
	
	protected String actorSystemName;
	
	protected boolean started;
	
	/**
	 * Initialise le serveur avec un port et un nom.
	 * 
	 * @param port le port sur lequel le système sera déployé
	 * @param actorSystemName le nom du système
	 */
	public Server(int port, String actorSystemName) {
		this.port = port;
		this.actorSystemName = actorSystemName;
	}
	
	/**
	 * Tente de démarrer le serveur et renvoie vrai si le serveur est bien démarré
	 * 
	 * @return vrai si le serveur a démarré, faux s'il avait déjà démarré ou si une erreur a eu lieu lors du démarrage
	 */
	public boolean startup() {
		if(!this.started){
			try{
				this.createActorSystem();
				this.started = true;
				return true;
			} catch(IllegalArgumentException e){
				return false;
			}
		}
		return false;
	}

	private void createActorSystem() throws IllegalArgumentException{
		Config config = ConfigFactory.load();
		Config portConfig = ConfigFactory.parseString("akka.remote.netty.tcp.port="+this.port);
		this.actorSystem = ActorSystem.create(this.actorSystemName, portConfig.withFallback(config));
	}

	/**
	 * Arrête le serveur.
	 */
	public void shutdown() {
		this.actorSystem.shutdown();
	}

	/**
	 * Interprète une ligne de commande et exécute une action sur le serveur si celle-ci est valide.
	 * Renvoie une chaine de caractère attestant de la réussite ou de l'échec d'une commande si sa synthaxe est valide,
	 * ou un message d'erreur si la commande est invalide.
	 * 
	 * @param line une ligne de texte contenant une commande interprétable par le serveur
	 * 
	 * @return un message faisant état de la réussite ou de l'échec de l'exécution de la commande.
	 */
	public String interprete(String line) {
		line = line.toLowerCase();
		String message;
		switch(line) {
			case "start":
			case "startup":
				message = this.startup() ? "Serveur démmaré" : "Erreur lors du démarrage du serveur";
				break;
			default:
				message = "Erreur : commande inconnue";
		}
		return message;
	}

}
