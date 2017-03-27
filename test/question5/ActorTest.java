package question5;
import org.junit.Test;

import car.tp3.message.IdMessage;
import car.tp3.message.NeighborMessage;
import car.tp3.question5.MySystemServer;
import junit.framework.Assert;
import scala.collection.mutable.StringBuilder;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


public class ActorTest {

	@Test
	public void testQuestion5() {
		
		List<String> actors1 = new ArrayList<String>();
		actors1.add("node1");
		actors1.add("node2");

		List<String> actors = new ArrayList<String>();
		actors.add("node3");
		actors.add("node4");
		
		MySystemServer server = new MySystemServer("server1", 9999, actors1);
		String url = server.getRemoteAddress("server1", "localhost", "9999", "node1");
		server.sendRemoteMessage(url, "node2", new NeighborMessage());
		
		MySystemServer server2 = new MySystemServer("server2", 9998, actors);
		
		url = server.getRemoteAddress("server1", "localhost", "9999", "node2");
		server2.sendRemoteMessage(url, "node3", new NeighborMessage());
		
		url = server.getRemoteAddress("server1", "localhost", "9999", "node2");
		server2.sendRemoteMessage(url, "node4", new NeighborMessage());
		
		
		// En attendant que les liaisons entre acteurs se fassent
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Envoi de message dans l'arbre
		url = server.getRemoteAddress("server1", "localhost", "9999", "node2");
		
		server2.sendRemoteMessage(url, null, new IdMessage("message1", "ceci est un test"));
		server2.sendRemoteMessage(url, null, new IdMessage("message2", "ceci est un autre test"));
		server2.sendRemoteMessage(url, null, new IdMessage("message1", "message déjà envoyé"));

		server.close();
		server2.close();
	}

}
