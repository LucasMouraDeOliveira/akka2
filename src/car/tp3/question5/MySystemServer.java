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

/**
 * Impl�mentation d'un actorSystem pour la question 5 du TP
 * 
 * @author Lucas Moura de Oliveira
 */
public class MySystemServer {
	
	protected ActorSystem actorSystem;
	
	protected Map<String, ActorRef> actors;
	
	/**
	 * Cr�e un actorSystem et initialise ses acteurs
	 * @param systemName le nom du syst�me
	 * @param port le port sur lequel le syst�me est d�marr�
	 * @param actorNames une liste de noms d'acteurs � initialiser
	 */
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
	
	/**
	 * Retourne la r�f�rence d'un acteur du syst�me
	 * @param actorName le nom de l'acteur
	 * @return une ActorRef d'un acteur du syst�me
	 */
	public ActorRef getLocalRef(String actorName) {
		return this.actors.get(actorName);
	}
	
	/**
	 * Envoie un message � un acteur du syst�me
	 * 
	 * @param actor le nom de l'acteur
	 * @param message le message � envoyer
	 */
	public void sendLocalMessage(String actor, Object message) {
		ActorRef ref = this.actors.get(actor);
		if(ref != null)
			ref.tell(message, ActorRef.noSender());
	}
	
	/**
	 * Envoie un message � un acteur d'un autre syst�me
	 * 
	 * @param url l'url du syst�me distant
	 * @param senderName le nom de l'acteur
	 * @param message le message � envoyer
	 */
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
	
	/**
	 * Retourne l'adresse d'un acteur sur un serveur distant
	 * 
	 * @param serverName l'identifiant du serveur
	 * @param address l'adresse du serveur
	 * @param port le port du serveur
	 * @param resource le nom de la ressource � atteindre sur le serveur distant
	 * 
	 * @return l'adresse de l'acteur distant
	 */
	public String getRemoteAddress(String serverName, String address, String port, String resource) {
		return "akka.tcp://"+serverName+"@"+address+":"+port+"/user/"+resource;
	}
	
}
