package car.tp3.question2;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import car.tp3.message.IncrementMessage;

/**
 * Implémentation d'un acteur pour la question 3 du TP
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class MyActor extends UntypedActor{
	
	protected List<ActorRef> refs;
	
	/**
	 * Initialise un acteur avec une liste de fils vides
	 */
	public MyActor(List<ActorRef> references){
		this.refs = new ArrayList<ActorRef>();
		for(ActorRef ref : references){
			this.refs.add(ref);
		}
	}
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		if(arg0 instanceof IncrementMessage){
			int nb = ((IncrementMessage)arg0).getNb()+1;
			System.out.println("[" + sender().path().name() + "->" + getSelf().path().name() + "] message : " + nb);
			for(ActorRef ref : refs){
				ref.tell(new IncrementMessage(nb), getSelf());
			}
		}
	}

}
