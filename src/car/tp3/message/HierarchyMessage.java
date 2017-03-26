package car.tp3.message;

import java.io.Serializable;

import akka.actor.ActorRef;

public class HierarchyMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 364349462335903142L;

	protected final ActorRef ref;
	
	protected final String type;
	
	public HierarchyMessage(ActorRef ref, String type){
		this.ref = ref;
		this.type = type;
	}
	
	public ActorRef getRef() {
		return ref;
	}
	
	public String getType() {
		return type;
	}

}
