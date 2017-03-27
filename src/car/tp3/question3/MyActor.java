package car.tp3.question3;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import car.tp3.message.HierarchyMessage;
import car.tp3.message.IncrementMessage;

/**
 * Implémentation d'un acteur pour la question 3 du TP
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class MyActor extends UntypedActor {

	protected ActorRef parent;

	protected List<ActorRef> refs;

	/**
	 * Initialise un acteur avec une liste de fils vides
	 */
	public MyActor() {
		this.refs = new ArrayList<ActorRef>();
	}

	@Override
	public void onReceive(Object data) throws Exception {
		if (data instanceof IncrementMessage) {
			int nb = ((IncrementMessage) data).getNb() + 1;
			System.out.println("[" + sender().path().name() + "->" + getSelf().path().name() + "] message : " + nb);
			for (ActorRef ref : refs) {
				ref.tell(new IncrementMessage(nb), getSelf());
			}
			if (this.parent != null && !sender().equals(this.parent)) {
				parent.tell(new IncrementMessage(nb), getSelf());
			}
		} else if (data instanceof HierarchyMessage) {
			HierarchyMessage message = (HierarchyMessage) data;
			System.out.println("[" + sender().path().name() + "->" + getSelf().path().name() + "] message : "
					+ message.getType() + " :  " + getSender().path().name());
			if ("parent".equalsIgnoreCase(message.getType())) {
				this.parent = getSender();
			} else if ("child".equalsIgnoreCase(message.getType())) {
				this.refs.add(getSender());
				getSender().tell(new HierarchyMessage("parent"), getSelf());
			}
		}
	}
}
