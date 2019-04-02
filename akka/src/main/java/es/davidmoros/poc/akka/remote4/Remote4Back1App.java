package es.davidmoros.poc.akka.remote4;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;

public final class Remote4Back1App {
	private static final String AKKA_CONFIG_FILENAME = "remote4/akka-remote4-back1.conf";
	public static void main(String[] args) throws InterruptedException {
		startup (new String[] {"2551"});
	}

	public static void startup(String[] ports) {
		for (String port : ports) {
			Config config = ConfigFactory
				.parseString(String.format("akka.remote.netty.tcp.port = %s", port))
				.withFallback(ConfigFactory.parseResources(AKKA_CONFIG_FILENAME));
		
			// Create an Akka system
			ActorSystem actorSystem = ActorSystem.create("akkaSystem", config);
			System.out.println(actorSystem);

			// Create a Router
			ActorRef dummyRouter = actorSystem.actorOf(FromConfig.getInstance().props(Props.create(DummyRouter.class)), "dummyRouter");
			System.out.println(String.format("dummyRouter: %s", dummyRouter));			
		}
	  }
}