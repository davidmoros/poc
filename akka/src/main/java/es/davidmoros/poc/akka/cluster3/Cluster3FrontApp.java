package es.davidmoros.poc.akka.cluster3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorPath;
import akka.actor.ActorPaths;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.cluster.client.ClusterClient;
import akka.cluster.client.ClusterClientSettings;

public final class Cluster3FrontApp {
	private static final String AKKA_CONFIG_FILENAME = "cluster3/akka-cluster3-front.conf";
	public static void main(String[] args) throws InterruptedException {
		startup ("5000", 20);
	}

	public static void startup(String frontPort, int numMessages) {
		Config config = ConfigFactory
		.parseString(String.format("akka.remote.netty.tcp.port = %s", frontPort))
		.withFallback(ConfigFactory.parseResources(AKKA_CONFIG_FILENAME));
	
		// Create an Akka system
		ActorSystem actorSystem = ActorSystem.create("akkaSystemClient", config);		
		System.out.println(actorSystem);

		// Create a cluster client
		ActorRef clusterClient = actorSystem.actorOf(ClusterClient.props(ClusterClientSettings.create(actorSystem).withInitialContacts(initialContacts())), "client");
		System.out.println(String.format("clusterClient: %s", clusterClient));

		for (int i = 1; i<=numMessages; i++) {
			clusterClient.tell(new ClusterClient.Send("/user/dummyRouter", String.format ("Hi %s from akkaSystemClient", i), true), ActorRef.noSender());
		}
	  }

	  private static Set<ActorPath> initialContacts() {
		return new HashSet<ActorPath>(Arrays.asList(
			ActorPaths.fromString("akka.tcp://akkaSystem@127.0.0.1:2551/system/receptionist"),
			ActorPaths.fromString("akka.tcp://akkaSystem@127.0.0.1:2552/system/receptionist")));
	  }	  
}