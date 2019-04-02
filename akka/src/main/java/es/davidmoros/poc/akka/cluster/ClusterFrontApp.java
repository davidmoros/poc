package es.davidmoros.poc.akka.cluster;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;

public final class ClusterFrontApp {
	private static final String AKKA_CONFIG_FILENAME = "cluster/akka-cluster-front.conf";
	public static void main(String[] args) throws InterruptedException {
		startup ("5000", new String[] {"2551"}, 10);
	}

	public static void startup(String frontPort, String[] backPorts, int messagesPerBack) {
		Config config = ConfigFactory
		.parseString(String.format("akka.remote.netty.tcp.port = %s", frontPort))
		.withFallback(ConfigFactory.parseResources(AKKA_CONFIG_FILENAME));
	
		// Create an Akka system
		ActorSystem actorSystem = ActorSystem.create("akkaSystemClient", config);		
		System.out.println(actorSystem);

		for (String port : backPorts) {
			for (int i = 1; i<=messagesPerBack; i++) {
				String akkaUri = String.format("akka.tcp://%s@%s:%s/user/%s", "akkaSystem", "127.0.0.1", port, "dummyRouter");;
				System.out.println(String.format("akkaUri: %s", akkaUri));
				
				ActorSelection actorSelection = actorSystem.actorSelection(akkaUri);
				System.out.println(String.format("actorSelection: %s", actorSelection));
				actorSelection.tell(String.format ("Hi %s:%s from akkaSystemClient", port, i), null);
			}
		}
	  }
}