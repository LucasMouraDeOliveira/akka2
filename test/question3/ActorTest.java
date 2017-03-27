package question3;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import car.tp3.message.HierarchyMessage;
import car.tp3.message.IncrementMessage;
import car.tp3.question3.MyActor;
import junit.framework.Assert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ActorTest {

	@Test
	public void testQuestion3() throws Exception {
		
		ActorSystem actorSystem = ActorSystem.create("system");
		
		TestActorRef<MyActor> parent, child1 , child2;
		parent = TestActorRef.create(actorSystem,Props.create(MyActor.class), "parent");
		child1 = TestActorRef.create(actorSystem,Props.create(MyActor.class), "child1");
		child2 = TestActorRef.create(actorSystem,Props.create(MyActor.class), "child2");
		ActorRef spyChild1 = spy(child1);

		parent.tell(new HierarchyMessage("child"), spyChild1);
		MyActor actorChild1 = child1.underlyingActor();
		Assert.assertEquals(actorChild1.getParent(),parent);
		
		parent.tell(new HierarchyMessage("child"), child2);
		MyActor actorChild2 = child2.underlyingActor();
		Assert.assertEquals(actorChild2.getParent(),parent);

		child2.tell(new IncrementMessage(0), ActorRef.noSender());
		
		
		ArgumentCaptor<IncrementMessage> argument = ArgumentCaptor.forClass(IncrementMessage.class);
		
		// 1 fois pour HierarchyMessage et 1 fois pour IncrementMessage
		verify(spyChild1,times(2)).tell(argument.capture(), any(ActorRef.class));
		Assert.assertEquals(argument.getValue().getNb(),2);

	}

}
