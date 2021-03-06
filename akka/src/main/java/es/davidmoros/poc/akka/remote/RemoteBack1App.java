package es.davidmoros.poc.akka.remote;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSystem;

public final class RemoteBack1App {
	private static final String AKKA_CONFIG_FILENAME = "remote/akka-remote-back1.conf";
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
		}
	  }
}