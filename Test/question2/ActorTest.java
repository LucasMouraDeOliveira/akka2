package question2;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import car.tp3.message.IncrementMessage;
import car.tp3.question2.MyActor;

import static org.mockito.Mockito.*;


public class ActorTest {

	@Test
	public void testQuestion2() throws Exception {
		
		ActorSystem actorSystem = ActorSystem.create("system");
		ActorRef child;
		List<ActorRef> childSons = new ArrayList<ActorRef>();
		
		child = actorSystem.actorOf(Props.create(MyActor.class, new ArrayList<ActorRef>()), "child");
		ActorRef spy = spy(child);
		childSons.add(spy);
		
		TestActorRef<MyActor> ref = TestActorRef.create(actorSystem, Props.create(MyActor.class, childSons), "parent");
		IncrementMessage message = mock(IncrementMessage.class);
		ref.tell(message, null);
		
		verify(message).getNb();
		
		MyActor actor = ref.underlyingActor();
		
		Assert.assertEquals(actor.getLastMessage(),message);
		
		ArgumentCaptor<IncrementMessage> argument = ArgumentCaptor.forClass(IncrementMessage.class);
	
		verify(spy).tell(argument.capture(), any(ActorRef.class));
		
		Assert.assertEquals(argument.getValue().getNb(),1);
	}

}
