package car.tp3.presentation;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;

/**
 * Serveur Akka pour la recette du TP3.
 * Le serveur contient un actorSystem lanc� sur un port pass� en param�tre et permet de :
 * <ul>
 * 	<li>D�marrer le syst�me</li>
 * 	<li>Arreter (shutdown) le syst�me</li>
 * 	<li>Ajouter un acteur � ce syst�me</li>
 * 	<li>Supprimer un acteur de ce syst�me</li>
 * 	<li>Envoyer un message � un acteur sur n'importe quel syst�me</li>
 * 	<li>Cr�er un lien (p�re/fils ou voisin) entre deux acteurs sur n'importe quel syst�me</li>
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
	 * @param port le port sur lequel le syst�me sera d�ploy�
	 * @param actorSystemName le nom du syst�me
	 */
	public Server(int port, String actorSystemName) {
		this.port = port;
		this.actorSystemName = actorSystemName;
	}
	
	/**
	 * Tente de d�marrer le serveur et renvoie vrai si le serveur est bien d�marr�
	 * 
	 * @return vrai si le serveur a d�marr�, faux s'il avait d�j� d�marr� ou si une erreur a eu lieu lors du d�marrage
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
	 * Arr�te le serveur.
	 */
	public void shutdown() {
		this.actorSystem.shutdown();
	}

	/**
	 * Interpr�te une ligne de commande et ex�cute une action sur le serveur si celle-ci est valide.
	 * Renvoie une chaine de caract�re attestant de la r�ussite ou de l'�chec d'une commande si sa synthaxe est valide,
	 * ou un message d'erreur si la commande est invalide.
	 * 
	 * @param line une ligne de texte contenant une commande interpr�table par le serveur
	 * 
	 * @return un message faisant �tat de la r�ussite ou de l'�chec de l'ex�cution de la commande.
	 */
	public String interprete(String line) {
		line = line.toLowerCase();
		String message;
		switch(line) {
			case "start":
			case "startup":
				message = this.startup() ? "Serveur d�mmar�" : "Erreur lors du d�marrage du serveur";
				break;
			default:
				message = "Erreur : commande inconnue";
		}
		return message;
	}

}
