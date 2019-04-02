package es.davidmoros.poc.akka.cluster3;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.client.ClusterClientReceptionist;
import akka.routing.FromConfig;

public final class Cluster3Back1App {
	private static final String AKKA_CONFIG_FILENAME = "cluster3/akka-cluster3-back.conf";
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

      		// Create an actor that handles cluster domain events
			actorSystem.actorOf(Props.create(ClusterListener.class), "clusterListener");			

			ActorRef dummyRouterReceptionist = actorSystem.actorOf(FromConfig.getInstance().props(Props.create(DummyRouter.class)), "dummyRouter");
			ClusterClientReceptionist.get(actorSystem).registerService(dummyRouterReceptionist);
		}
	}
}