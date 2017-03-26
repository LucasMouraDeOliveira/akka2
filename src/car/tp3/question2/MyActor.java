package car.tp3.question2;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class MyActor extends UntypedActor{
	
	protected List<ActorRef> refs;
	
	public MyActor(List<ActorRef> references){
		this.refs = new ArrayList<ActorRef>();
		for(ActorRef ref : references){
			this.refs.add(ref);
		}
	}
	
	@Override
	public void onReceive(Object arg0) throws Exception {
		if(arg0 instanceof MessageIncrement){
			int nb = ((MessageIncrement)arg0).getNb()+1;
			System.out.println("[" + sender().path().name() + "->" + getSelf().path().name() + "] message : " + nb);
			for(ActorRef ref : refs){
				ref.tell(new MessageIncrement(nb), getSelf());
			}
		}
	}

}
