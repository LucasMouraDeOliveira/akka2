package car.tp3.presentation;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.AddressFromURIString;
import akka.actor.Deploy;
import akka.actor.Props;
import akka.remote.RemoteScope;
import car.tp3.message.ExtendedNeighborMessage;
import car.tp3.message.IdMessage;
import car.tp3.message.ShutdownMessage;

/**
 * Client Akka pour la recette du TP3. Le client contient un actorSystem lancé
 * sur un port aléatoire et permet de :
 * <ul>
 * <li>Démarrer le système</li>
 * <li>Arreter (shutdown) le système</li>
 * <li>Ajouter un acteur à un système</li>
 * <li>Supprimer un acteur (et tous ses liens de voisinage)</li>
 * <li>Envoyer un message à un acteur sur n'importe quel système</li>
 * <li>Créer un lien (père/fils ou voisin) entre deux acteurs sur n'importe quel
 * système</li>
 * <li>Supprimer un lien entre deux acteurs</li>
 * </ul>
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class Client {

	protected ActorSystem actorSystem;

	protected boolean started;

	protected Map<String, ActorRef> remoteActors;

	/**
	 * Initialise le client.
	 */
	public Client() {
		this.remoteActors = new HashMap<String, ActorRef>();
	}

	/**
	 * Tente de démarrer le serveur et renvoie vrai si le serveur est bien
	 * démarré
	 * 
	 * @return vrai si le serveur a démarré, faux s'il avait déjà démarré ou si
	 *         une erreur a eu lieu lors du démarrage
	 */
	public boolean start() {
		if (!this.started) {
			try {
				this.createActorSystem();
				this.started = true;
				return true;
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
		return false;
	}

	private void createActorSystem() throws IllegalArgumentException {
		Config config = ConfigFactory.load();
		this.actorSystem = ActorSystem.create("client", config);
	}

	/**
	 * Arrête le serveur.
	 */
	public void shutdown() {
		if (this.started) {
			this.actorSystem.shutdown();
			this.started = false;
		}
	}

	/**
	 * Interprète une ligne de commande et exécute une action sur le serveur si
	 * celle-ci est valide. Renvoie une chaine de caractère attestant de la
	 * réussite ou de l'échec d'une commande si sa synthaxe est valide, ou un
	 * message d'erreur si la commande est invalide.
	 * 
	 * @param line
	 *            une ligne de texte contenant une commande interprétable par le
	 *            serveur
	 * 
	 * @return un message faisant état de la réussite ou de l'échec de
	 *         l'exécution de la commande.
	 */
	public String interprete(String line) {
		line = line.toLowerCase();
		String[] params = line.split(" ");
		String command = params[0];
		String message;
		switch (command) {
		case "create":
			message = create(params);
			break;
		case "delete":
			message = delete(params);
			break;
		case "send":
			message = send(params);
			break;
		case "link":
			message = link(params);
			break;
		case "unlink":
			message = unlink(params);
			break;
		default:
			message = "Erreur : commande inconnue";
		}
		return message;
	}

	private String unlink(String[] command) {
		// Il faut 2 paramètres (en plus du nom de commande) : nom de l'acteur 1
		// et nom de l'acteur 2
		// et message
		if (command.length != 3) {
			return "Erreur : nombre de paramètres invalide."
					+ "\nLe format attendu est le suivant : unlink <nom_acteur1> <nom_acteur2>";
		}
		ActorRef actor1 = this.remoteActors.get(command[1]);
		if (actor1 == null)
			return "Erreur : acteur 1 inconnu";
		ActorRef actor2 = this.remoteActors.get(command[2]);
		if (actor2 == null)
			return "Erreur : acteur 2 inconnu";
		this.unlinkActors(actor1, actor2);
		return "liaison effectuée avec succès";
	}

	private void unlinkActors(ActorRef actor1, ActorRef actor2) {
		actor1.tell(new ExtendedNeighborMessage("delete"), actor2);
		actor2.tell(new ExtendedNeighborMessage("delete"), actor1);
	}

	private String link(String[] command) {
		// Il faut 2 paramètres (en plus du nom de commande) : nom de l'acteur
		// et message
		if (command.length != 3) {
			return "Erreur : nombre de paramètres invalide."
					+ "\nLe format attendu est le suivant : link <nom_acteur1> <nom_acteur2>";
		}
		ActorRef actor1 = this.remoteActors.get(command[1]);
		if (actor1 == null)
			return "Erreur : acteur 1 inconnu";
		ActorRef actor2 = this.remoteActors.get(command[2]);
		if (actor2 == null)
			return "Erreur : acteur 2 inconnu";
		this.linkActors(actor1, actor2);
		return "liaison effectuée avec succès";
	}

	private void linkActors(ActorRef actor1, ActorRef actor2) {
		actor1.tell(new ExtendedNeighborMessage("add"), actor2);
		actor2.tell(new ExtendedNeighborMessage("add"), actor1);
	}

	private String send(String[] command) {
		// Il faut 2 paramètres (en plus du nom de commande) : nom de l'acteur
		// et message
		if (command.length != 3) {
			return "Erreur : nombre de paramètres invalide."
					+ "\nLe format attendu est le suivant : send <nom_acteur> <message>";
		}
		ActorRef actorRef = this.remoteActors.get(command[1]);
		if (actorRef == null) {
			return "Erreur : acteur introuvable";
		} else {
			this.sendMessageTo(actorRef, command[2]);
			return "Succès";
		}
	}

	private void sendMessageTo(ActorRef actorRef, String message) {
		UUID id = UUID.randomUUID();
		actorRef.tell(new IdMessage(id.toString(), message), ActorRef.noSender());
	}

	private String create(String[] command) {
		// Il faut 4 paramètres (en plus du nom de commande : nom du serveur,
		// adresse, port, et nom de l'acteur)
		if (command.length != 5) {
			return "Erreur : nombre de paramètres invalide."
					+ "\nLe format attendu est le suivant : create <nom_serveur> <adresse> <port> <nom_acteur>";
		}
		if(this.remoteActors.containsKey(command[4])){
			return "Erreur : le nom d'acteur est déjà utilisé, veuillez en trouver un nouveau";
		}
		String address = "akka.tcp://" + command[1] + "@" + command[2] + ":" + command[3];
		Address addr = AddressFromURIString.parse(address);
		this.createActor(addr, command[4]);
		return "Acteur " + command[4] + " crée avec succès sur " + command[1];
	}

	private String delete(String[] command) {
		// Il faut 1 paramètre (en plus du nom de commande) : nom de l'acteur
		if (command.length != 2) {
			return "Erreur : nombre de paramètres invalide."
					+ "\nLe format attendu est le suivant : delete <nom_acteur>";
		}
		// Référence de l'acteur dans la map
		ActorRef actorRef = this.remoteActors.get(command[1]);
		if (actorRef == null) {
			return "Erreur : acteur introuvable";
		} else {
			this.deleteActor(actorRef);
			this.remoteActors.remove(command[1]);
			System.out.println("Removed " + command[1] + " from refs");
			return "Succès";
		}
	}

	private void deleteActor(ActorRef actorRef) {
		actorRef.tell(new ShutdownMessage(), ActorRef.noSender());
	}

	private void createActor(Address addr, String actorName) {
		ActorRef actorRef = this.actorSystem
				.actorOf(Props.create(Node.class).withDeploy(new Deploy(new RemoteScope(addr))), actorName);
		this.remoteActors.put(actorName, actorRef);
		System.out.println("Added " + actorName + " to refs");
	}

}
