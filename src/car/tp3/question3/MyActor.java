package car.tp3.question3;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import car.tp3.message.HierarchyMessage;
import car.tp3.message.IncrementMessage;

public class MyActor extends UntypedActor{
	
	protected ActorRef parent;
	
	protected List<ActorRef> refs;
	
	public MyActor(){
		this.refs = new ArrayList<ActorRef>();
	}
	
	@Override
	public void onReceive(Object data) throws Exception {
		if(data instanceof IncrementMessage){
			int nb = ((IncrementMessage)data).getNb()+1;
			System.out.println("[" + sender().path().name() + "->" + getSelf().path().name() + "] message : " + nb);
			for(ActorRef ref : refs){
				ref.tell(new IncrementMessage(nb), getSelf());
			}
			if(this.parent != null && !sender().equals(this.parent)){
				parent.tell(new IncrementMessage(nb), getSelf());
			}
		} else if (data instanceof HierarchyMessage){
			HierarchyMessage message = (HierarchyMessage)data;
			System.out.println("[" + sender().path().name() + "->" + getSelf().path().name() + "] message : " + message.getType() + " :  " + message.getRef().path().name());
			if("parent".equalsIgnoreCase(message.getType())) {
				this.parent = message.getRef();
			} else if("child".equalsIgnoreCase(message.getType())) {
				this.refs.add(message.getRef());
			}
		}
	}
}
