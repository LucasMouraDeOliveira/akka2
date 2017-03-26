package car.tp3.question5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MySystemServer {
	
	protected ActorSystem actorSystem;
	
	protected Map<String, ActorRef> actors;
	
	public MySystemServer(String systemName, int port, List<String> actorNames) {
		this.createActorSystem(systemName, port);
		this.createActors(actorNames);
	}
	
	private void createActorSystem(String systemName, int port) {
		Config overrides = ConfigFactory.parseString("akka.remote.netty.tcp.port = " + port);
		Config actualConfig = overrides.withFallback(ConfigFactory.load());
		this.actorSystem = ActorSystem.create(systemName, actualConfig);
	}
	
	private void createActors(List<String> actorNames) {
		this.actors = new HashMap<String, ActorRef>();
		ActorRef actorRef;
		for(String s : actorNames){
			actorRef = actorSystem.actorOf(Props.create(MyActor.class), s);
			actors.put(s, actorRef);
		}
	}
	
	public ActorRef getLocalRef(String actorName) {
		return this.actors.get(actorName);
	}
	
	public void sendLocalMessage(String actor, Object message) {
		ActorRef ref = this.actors.get(actor);
		if(ref != null)
			ref.tell(message, ActorRef.noSender());
	}
	
	public void sendRemoteMessage(String url, String senderName, Object message) {
		ActorSelection selection = this.actorSystem.actorSelection(url);
		ActorRef sender = null;
		if(senderName == null)
			sender = ActorRef.noSender();
		else
			sender = getLocalRef(senderName);
		if(selection != null)
			selection.tell(message, sender);
	}
	
	public String getRemoteAddress(String serverName, String address, String port, String resource) {
		return "akka.tcp://"+serverName+"@"+address+":"+port+"/user/"+resource;
	}
	
}
