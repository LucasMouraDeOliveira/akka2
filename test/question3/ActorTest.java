package question3;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import car.tp3.message.HierarchyMessage;
import car.tp3.message.IncrementMessage;
import car.tp3.question2.MyActor;

import static org.mockito.Mockito.*;


public class ActorTest {

	@Test
	public void test() throws Exception {
		
		// Création du système
				ActorSystem actorSystem = ActorSystem.create("system");
				
				// Création des acteurs
				ActorRef parent, child1 , child2;
				parent = actorSystem.actorOf(Props.create(MyActor.class), "parent");
				child1 = actorSystem.actorOf(Props.create(MyActor.class), "child1");
				child2 = actorSystem.actorOf(Props.create(MyActor.class), "child2");
				
				// Création de l'arborescence
				parent.tell(new HierarchyMessage("child"), child1);
				parent.tell(new HierarchyMessage("child"), child2);

				child2.tell(new IncrementMessage(0), ActorRef.noSender());
	}

}
