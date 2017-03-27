package car.tp3.question4;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import car.tp3.message.HierarchyMessage;
import car.tp3.message.IncrementMessage;

/**
 * Implémentation d'un acteur pour la question 4 du TP
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class MyActor extends UntypedActor{
	
	protected ActorRef parent;
	
	protected List<ActorRef> refs;
	
	/**
	 * Initialise l'acteur avec une liste de fils vide
	 */
	public MyActor(){
		this.refs = new ArrayList<ActorRef>();
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof HierarchyMessage) {
			HierarchyMessage hMessage = (HierarchyMessage)message;
			this.log(getSender(), hMessage.getType());
			if("parent".equalsIgnoreCase(hMessage.getType())) {
				this.parent = getSender();
			} else if("child".equalsIgnoreCase(hMessage.getType())) {
				this.refs.add(getSender());
				// L'acteur doit informer son fils qu'il est son père
				getSender().tell(new HierarchyMessage("parent"), this.getSelf());
			}
		} else if(message instanceof IncrementMessage){
			int nb = ((IncrementMessage)message).getNb();
			this.log(getSender(), " valeur ("+nb+")");
			for(ActorRef ref : refs){
				if(!sender().equals(ref)){
					ref.tell(new IncrementMessage(nb+1), this.getSelf());
				}
			}
			if(this.parent != null && !sender().equals(this.parent)){
				parent.tell(new IncrementMessage(nb+1), this.getSelf());
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
