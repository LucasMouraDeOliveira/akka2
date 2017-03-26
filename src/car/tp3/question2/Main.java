package car.tp3.question2;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
	
	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("system");
		ActorRef parent, child2, child3, child4, child5, child6;
		
		child3 = actorSystem.actorOf(Props.create(MyActor.class, new ArrayList<ActorRef>()), "child3");
		child4 = actorSystem.actorOf(Props.create(MyActor.class, new ArrayList<ActorRef>()), "child4");
		child6 = actorSystem.actorOf(Props.create(MyActor.class, new ArrayList<ActorRef>()), "child6");
		
		List<ActorRef> child2Sons = new ArrayList<ActorRef>();
		child2Sons.add(child3);
		child2Sons.add(child4);
		child2 = actorSystem.actorOf(Props.create(MyActor.class, child2Sons), "child2");
		
		List<ActorRef> child5Sons = new ArrayList<ActorRef>();
		child5Sons.add(child6);
		child5 = actorSystem.actorOf(Props.create(MyActor.class, child5Sons), "child5");
		
		List<ActorRef> rootSons = new ArrayList<ActorRef>();
		rootSons.add(child2);
		rootSons.add(child5);
		parent = actorSystem.actorOf(Props.create(MyActor.class, rootSons), "parent");
		
		parent.tell(new MessageIncrement(0), null);
	}

}
