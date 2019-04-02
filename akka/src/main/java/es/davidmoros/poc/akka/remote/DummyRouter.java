package es.davidmoros.poc.akka.remote;

import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class DummyRouter extends UntypedAbstractActor {
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	@Override
	public void onReceive(Object o) throws InterruptedException {
		log.info("Actor {}. Message received: \"{}\".", this, o);
		//System.out.println(String.format("Actor %s. Message received: \"%s\".", this, o));

		Thread.sleep(500);
	}
}
