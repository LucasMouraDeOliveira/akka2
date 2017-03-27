package question4;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import car.tp3.message.HierarchyMessage;
import car.tp3.message.IncrementMessage;
import car.tp3.question3.MyActor;
import car.tp3.question4.MySystemServer;
import junit.framework.Assert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;


public class ActorTest {

	@Test
	public void testQuestion4() {
		
		List<String> actors = new ArrayList<String>();
		actors.add("node1");
		actors.add("node2");

		List<String> actors2 = new ArrayList<String>();
		actors2.add("node3");
		actors2.add("node4");
		
		MySystemServer server = new MySystemServer("server1", 9999, actors);
		String url = server.getRemoteAddress("server1", "localhost", "9999", "node1");
		
		Assert.assertEquals("akka.tcp://server1@localhost:9999/user/node1", url);
		
		server.sendRemoteMessage(url, "node2", new HierarchyMessage("child"));
		
		MySystemServer server2 = new MySystemServer("server2", 9998, actors2);
		url = server2.getRemoteAddress("server2", "localhost", "9998", "node3");
		
		Assert.assertEquals("akka.tcp://server2@localhost:9998/user/node3", url);

		server2.sendRemoteMessage(url, "node4", new HierarchyMessage("child"));
		

		url = server.getRemoteAddress("server1", "localhost", "9999", "node2");
		
		Assert.assertEquals("akka.tcp://server1@localhost:9999/user/node2", url);

		server2.sendRemoteMessage(url, "node4", new HierarchyMessage("child"));
		
		try {
			Thread.sleep(200);// attend que tous les message soit envoyer
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Parcours de l'arbre
		url = server2.getRemoteAddress("server2", "localhost", "9998", "node4");
		
		Assert.assertEquals("akka.tcp://server2@localhost:9998/user/node4", url);

		server2.sendRemoteMessage(url, null, new IncrementMessage(0));

		server.close();
		server2.close();
	}

}
