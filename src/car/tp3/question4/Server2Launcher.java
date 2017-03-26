package car.tp3.question4;

import java.util.ArrayList;
import java.util.List;

import car.tp3.message.HierarchyMessage;
import car.tp3.message.IncrementMessage;

public class Server2Launcher {
	
	public static void main(String[] args) {
		List<String> actors = new ArrayList<String>();
		actors.add("node3");
		actors.add("node4");
		actors.add("node6");
		MySystemServer server = new MySystemServer("server2", 9998, actors);
		server.sendRemoteMessage("server1", "localhost", "9999", "node2", 
				new HierarchyMessage(server.getLocalRef("node3"), "child"));
		server.sendRemoteMessage("server1", "localhost", "9999", "node2", 
				new HierarchyMessage(server.getLocalRef("node4"), "child"));
		server.sendRemoteMessage("server1", "localhost", "9999", "node5", 
				new HierarchyMessage(server.getLocalRef("node6"), "child"));
		server.sendRemoteMessage("server1", "localhost", "9999", "node2", new IncrementMessage(0));
	}

}
