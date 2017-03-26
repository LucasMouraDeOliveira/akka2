package car.tp3.question3;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import car.tp3.message.HierarchyMessage;
import car.tp3.message.IncrementMessage;

public class Main {
	
	public static void main(String[] args) {
		
		// Création du système
		ActorSystem actorSystem = ActorSystem.create("system");
		
		// Création des acteurs
		ActorRef parent, child2, child3, child4, child5, child6;
		parent = actorSystem.actorOf(Props.create(MyActor.class), "parent");
		child2 = actorSystem.actorOf(Props.create(MyActor.class), "child2");
		child3 = actorSystem.actorOf(Props.create(MyActor.class), "child3");
		child5 = actorSystem.actorOf(Props.create(MyActor.class), "child5");
		child4 = actorSystem.actorOf(Props.create(MyActor.class), "child4");
		child6 = actorSystem.actorOf(Props.create(MyActor.class), "child6");
		
		// Création de l'arborescence
		parent.tell(new HierarchyMessage(child2, "child"), ActorRef.noSender());
		parent.tell(new HierarchyMessage(child5, "child"), ActorRef.noSender());
		
		child2.tell(new HierarchyMessage(parent, "parent"), ActorRef.noSender());
		child2.tell(new HierarchyMessage(child3, "child"), ActorRef.noSender());
		child2.tell(new HierarchyMessage(child4, "child"), ActorRef.noSender());
		
		child5.tell(new HierarchyMessage(parent, "parent"), ActorRef.noSender());
		child5.tell(new HierarchyMessage(child6, "child"), ActorRef.noSender());
		
		child3.tell(new HierarchyMessage(child2, "parent"), ActorRef.noSender());
		
		child4.tell(new HierarchyMessage(child2, "parent"), ActorRef.noSender());
		
		child6.tell(new HierarchyMessage(child5, "parent"), ActorRef.noSender());
		
		// Envoi de message
		child2.tell(new IncrementMessage(0), ActorRef.noSender());
	}

}
