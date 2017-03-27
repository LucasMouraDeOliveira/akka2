package car.tp3.question5;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

/**
 * Implémentation d'un acteur pour la question 5 du TP.
 * Chaque acteur possède une liste de voisins à qui il transmet les messages qu'il reçoit
 * 
 * @author Lucas Moura de Oliveira
 */
public class MyActor extends UntypedActor{
	
	protected List<ActorRef> neighbors;
	
	protected List<String> messageReceived;
	
	/**
	 * Initialise un acteur avec une liste de voisins vides
	 */
	public MyActor() {
		this.neighbors = new ArrayList<ActorRef>();
		this.messageReceived = new ArrayList<String>();
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof IdMessage) {
			IdMessage idMessage = (IdMessage)message;
			// Si on n'a pas déjà reçu le message, on le transmet aux voisins (sauf l'envoyeur)
			if(!messageReceived.contains(idMessage.getId())){
				this.messageReceived.add(idMessage.getId());
				this.log(getSender(), idMessage.getMessage());
				for(ActorRef ref : this.neighbors){
					if(!ref.equals(this.getSender())){
						ref.tell(message, this.getSelf());
					}
				}
			}
		} else if(message instanceof NeighborMessage) {
			if(!neighbors.contains(getSender())){
				this.log(getSender(), "add neighbor");
				this.neighbors.add(getSender());
				getSender().tell(new NeighborMessage(), getSelf());
			}
		}
	}
	
	/**
	 * Affiche un message reçu dans la console 
	 * @param sender l'expéditeur du message
	 * @param message le contenu du message
	 */
	public void log(ActorRef sender, String message){
		System.out.println("[" + sender().path().name() + "->" + getSelf().path().name() + "] message = " + message);
	}

}
