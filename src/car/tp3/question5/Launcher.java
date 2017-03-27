package car.tp3.question5;

import java.util.ArrayList;
import java.util.List;

/**
 * Launcher pour l'exercice 5 du TP
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class Launcher {
	
	/**
	 * Lance l'implémentation de la question 5 du tp.
	 * Les acteurs sont répartis sur deux systèmes distants, puis les liaisons de voisinages sont créées.
	 * Enfin, trois message (dont un doublon) sont envoyés aux acteurs.
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// Server 1
		List<String> actors1 = new ArrayList<String>();
		actors1.add("node1");
		actors1.add("node2");
		actors1.add("node5");
		MySystemServer server = new MySystemServer("server1", 9999, actors1);
		String url = server.getRemoteAddress("server1", "localhost", "9999", "node1");
		server.sendRemoteMessage(url, "node2", new NeighborMessage());
		server.sendRemoteMessage(url, "node5", new NeighborMessage());
		
		// Server 2
		List<String> actors = new ArrayList<String>();
		actors.add("node3");
		actors.add("node4");
		actors.add("node6");
		MySystemServer server2 = new MySystemServer("server2", 9998, actors);
		
		url = server.getRemoteAddress("server1", "localhost", "9999", "node2");
		server2.sendRemoteMessage(url, "node3", new NeighborMessage());
		
		url = server.getRemoteAddress("server1", "localhost", "9999", "node2");
		server2.sendRemoteMessage(url, "node4", new NeighborMessage());
		
		url = server.getRemoteAddress("server1", "localhost", "9999", "node5");
		server2.sendRemoteMessage(url, "node6", new NeighborMessage());
		
		url = server.getRemoteAddress("server2", "localhost", "9998", "node4");
		server2.sendRemoteMessage(url, "node6", new NeighborMessage());
		
		// En attendant que les liaisons entre acteurs se fassent
		Thread.sleep(5000);
		// Envoi de message dans l'arbre
		url = server.getRemoteAddress("server1", "localhost", "9999", "node5");
		
		server2.sendRemoteMessage(url, null, new IdMessage("message1", "ceci est un test"));
		server2.sendRemoteMessage(url, null, new IdMessage("message2", "ceci est un autre test"));
		server2.sendRemoteMessage(url, null, new IdMessage("message1", "message déjà envoyé"));
	}

}
