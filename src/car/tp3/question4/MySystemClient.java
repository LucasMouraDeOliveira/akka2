package car.tp3.question4;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import car.tp3.message.SimpleMessage;

public class MySystemClient {
	
	public MySystemClient() {
		ActorSystem actorSystem = ActorSystem.create("MySystemClient");
		String url = "akka.tcp://MySystemServer@localhost:2552/greeter4";
		ActorSelection greeter4 = actorSystem.actorSelection(url);
		greeter4.tell(new SimpleMessage("coucou"), ActorRef.noSender());
	}

}