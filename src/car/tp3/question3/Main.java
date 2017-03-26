package car.tp3.question3;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import car.tp3.message.HierarchyMessage;
import car.tp3.message.IncrementMessage;

public class Main {
	
	public static void main(String[] args) {
		
		// Cr�ation du syst�me
		ActorSystem actorSystem = ActorSystem.create("system");
		
		// Cr�ation des acteurs
		ActorRef parent, child2, child3, child4, child5, child6;
		parent = actorSystem.actorOf(Props.create(MyActor.class), "parent");
		child2 = actorSystem.actorOf(Props.create(MyActor.class), "child2");
		child3 = actorSystem.actorOf(Props.create(MyActor.class), "child3");
		child5 = actorSystem.actorOf(Props.create(MyActor.class), "child5");
		child4 = actorSystem.actorOf(Props.create(MyActor.class), "child4");
		child6 = actorSystem.actorOf(Props.create(MyActor.class), "child6");
		
		// Cr�ation de l'arborescence
		parent.tell(new HierarchyMessage("child"), child2);
		parent.tell(new HierarchyMessage("child"), child5);
		
		child2.tell(new HierarchyMessage("child"), child3);
		child2.tell(new HierarchyMessage("child"), child4);
		
		child5.tell(new HierarchyMessage("child"), child6);
		
		// Envoi de message
		child2.tell(new IncrementMessage(0), ActorRef.noSender());
	}

}
