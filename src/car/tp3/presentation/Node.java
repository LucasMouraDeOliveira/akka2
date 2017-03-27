package car.tp3.presentation;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import car.tp3.question5.IdMessage;

/**
 * Acteur de l'application répartie, relié à d'autres noeuds par une liste de voisins.
 * A la réception d'un message valide ({@link IdMessage}), le noeud vérifie l'identifiant du message,
 * et si cet identifiant est inconnu (le message n'a pas encore été traité), il le propage à ses voisins.
 * 
 * Les messages peuvent venir de deux sources : soit c'est un voisin qui l'a envoyé (et dans ce cas le message
 * n'est pas renvoyé à ce voisin là), soit c'est un message venant d'un utilisateur (sans référence de sender),
 * et le message est transmis à tous les voisins.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class Node extends UntypedActor{
	
	protected List<ActorRef> neighbors;
	
	protected List<String> messages;
	
	/**
	 * Crée un noeud avec une liste de voisins vide
	 */
	public Node() {
		this.neighbors = new ArrayList<ActorRef>();
		this.messages = new ArrayList<String>();
	}
	
	private void addNeighbor(ActorRef ref) {
		this.neighbors.add(ref);
	}
	
	private void removeNeighbor(ActorRef ref) {
		this.neighbors.remove(ref);
	}
	
	private void addMessage(IdMessage message) {
		this.messages.add(message.getId());
	}
	
	private boolean hasAlreadyReceivedMessage(IdMessage iMessage) {
		return this.messages.contains(iMessage.getId());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof NeighborMessage) {
			NeighborMessage nMessage = (NeighborMessage)message;
			if(nMessage.getAction().equals("add")){
				this.log("added sender as neighbor");
				this.addNeighbor(getSender());
			} else if(nMessage.getAction().equals("delete")){
				this.log("removed sender from neighbors");
				this.removeNeighbor(getSender());
			}
		} else if (message instanceof IdMessage){
			IdMessage iMessage = (IdMessage)message;
			if(!this.hasAlreadyReceivedMessage(iMessage)){
				// On note que le message a déjà été reçu
				this.addMessage(iMessage);
				this.log(iMessage.getMessage());
				for(ActorRef actorRef : neighbors) {
					actorRef.tell(iMessage, getSelf());
				}
			}
		} else if(message instanceof ShutdownMessage) {
			for(ActorRef ref : this.neighbors){
				ref.tell(new NeighborMessage("delete"), this.getSelf());
			}
			// On arrête l'acteur
			context().stop(getSelf());
		} else {
			unhandled(message);
		}
	}

	private void log(String message){
		System.out.println("["+this.getSender().path().name()+"->"+this.getSelf().path().name()+"] " + message);
	}

}
