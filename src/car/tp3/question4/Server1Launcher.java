package car.tp3.question4;

import java.util.ArrayList;
import java.util.List;

public class Server1Launcher {

	public static void main(String[] args) {
		List<String> actors1 = new ArrayList<String>();
		actors1.add("node1");
		actors1.add("node2");
		actors1.add("node5");
		new MySystemServer("server1", 9999, actors1);
	}

}
