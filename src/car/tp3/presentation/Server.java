package car.tp3.presentation;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;

public class Server {
	
	protected ActorSystem actorSystem;
	
	public Server(int port, String name){
		this.createActorSystem(port, name);
	}
	
	private void createActorSystem(int port, String name) throws IllegalArgumentException {
		Config config = ConfigFactory.load();
		Config portConfig = ConfigFactory.parseString("akka.remote.netty.tcp.port="+port);
		this.actorSystem = ActorSystem.create(name, portConfig.withFallback(config));
	}
	
	public static void main(String[] args) {
		if(args.length < 2){
			System.out.println("Veuillez renseigner un port et un nom de serveur");
		} else {
			try{
				int port = Integer.valueOf(args[0]);
				String name = args[1];
				if(name.isEmpty()){
					System.out.println("Veuillez renseigner un nom de serveur");
				} else {
					new Server(port, name);
				}
			} catch(NumberFormatException e){
				System.out.println("Veuillez renseigner un numéro de port");
			}
		}
	}

}
