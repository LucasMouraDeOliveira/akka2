package car.tp3.presentation;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import car.tp3.question5.IdMessage;

/**
 * Acteur de l'application r�partie, reli� � d'autres noeuds par une liste de voisins.
 * A la r�ception d'un message valide ({@link IdMessage}), le noeud v�rifie l'identifiant du message,
 * et si cet identifiant est inconnu (le message n'a pas encore �t� trait�), il le propage � ses voisins.
 * 
 * Les messages peuvent venir de deux sources : soit c'est un voisin qui l'a envoy� (et dans ce cas le message
 * n'est pas renvoy� � ce voisin l�), soit c'est un message venant d'un utilisateur (sans r�f�rence de sender),
 * et le message est transmis � tous les voisins.
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class Node extends UntypedActor{
	
	protected List<ActorRef> neighbors;
	
	protected List<String> messages;
	
	/**
	 * Cr�e un noeud avec une liste de voisins vide
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
				// On note que le message a d�j� �t� re�u
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
			// On arr�te l'acteur
			context().stop(getSelf());
		} else {
			unhandled(message);
		}
	}

	private void log(String message){
		System.out.println("["+this.getSender().path().name()+"->"+this.getSelf().path().name()+"] " + message);
	}

}
