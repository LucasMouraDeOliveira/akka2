package car.tp3.question4;

import java.util.ArrayList;
import java.util.List;

import car.tp3.message.HierarchyMessage;
import car.tp3.message.IncrementMessage;

/**
 * Launcher pour l'exercice 4 du TP
 * 
 * @author Lucas Moura de Oliveira
 *
 */
public class ServerLauncher {

	/**
	 * Lance l'implémentation de la question 4 du tp. Les acteurs sont répartis
	 * sur deux systèmes distants, puis les liaisons père/fils sont créées.
	 * Enfin, un message de test (compteur) est envoyé à un des acteurs et
	 * parcours l'arbre.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// Server 1
		List<String> actors1 = new ArrayList<String>();
		actors1.add("node1");
		actors1.add("node2");
		actors1.add("node5");
		MySystemServer server = new MySystemServer("server1", 9999, actors1);
		String url = server.getRemoteAddress("server1", "localhost", "9999", "node1");
		server.sendRemoteMessage(url, "node2", new HierarchyMessage("child"));
		server.sendRemoteMessage(url, "node5", new HierarchyMessage("child"));

		// Server 2
		List<String> actors = new ArrayList<String>();
		actors.add("node3");
		actors.add("node4");
		actors.add("node6");
		MySystemServer server2 = new MySystemServer("server2", 9998, actors);

		url = server.getRemoteAddress("server1", "localhost", "9999", "node2");
		server2.sendRemoteMessage(url, "node3", new HierarchyMessage("child"));

		url = server.getRemoteAddress("server1", "localhost", "9999", "node2");
		server2.sendRemoteMessage(url, "node4", new HierarchyMessage("child"));

		url = server.getRemoteAddress("server1", "localhost", "9999", "node5");
		server2.sendRemoteMessage(url, "node6", new HierarchyMessage("child"));

		// Parcours de l'arbre
		url = server.getRemoteAddress("server1", "localhost", "9999", "node5");
		server2.sendRemoteMessage(url, null, new IncrementMessage(0));

	}

}
