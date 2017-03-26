package car.tp3.question4;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import car.tp3.message.HierarchyMessage;
import car.tp3.message.IncrementMessage;
import car.tp3.message.SimpleMessage;

public class MyActor extends UntypedActor{
	
	protected ActorRef parent;
	
	protected List<ActorRef> refs;
	
	public MyActor(){
		this.refs = new ArrayList<ActorRef>();
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof SimpleMessage){
			SimpleMessage sMessage = (SimpleMessage)message;
			this.log(sMessage.getMessage());
		} else if(message instanceof HierarchyMessage) {
			HierarchyMessage hMessage = (HierarchyMessage)message;
			System.out.println("[" + sender().path().name() + "->" + getSelf().path().name() + "] message : " + hMessage.getType() + " :  " + hMessage.getRef().path().name());
			if("parent".equalsIgnoreCase(hMessage.getType())) {
				this.parent = hMessage.getRef();
			} else if("child".equalsIgnoreCase(hMessage.getType())) {
				this.refs.add(hMessage.getRef());
			}
		} else if(message instanceof IncrementMessage){
			int nb = ((IncrementMessage)message).getNb();
			this.log(""+nb);
			for(ActorRef ref : refs){
				ref.tell(new IncrementMessage(nb+1), this.getSelf());
			}
		} else {
			System.out.println(message.toString());
		}
	}
	
	public void log(String message){
		System.out.println("Message reçu : " + message);
	}

}
