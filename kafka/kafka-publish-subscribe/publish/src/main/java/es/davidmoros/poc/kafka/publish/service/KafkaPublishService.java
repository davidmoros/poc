package es.davidmoros.poc.kafka.publish.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaPublishService {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void publish(@NonNull String topic, @NonNull String message) {
		
		kafkaTemplate.send(topic, message).addCallback(new KafkaPublishCallback());
	}
	
	private class KafkaPublishCallback implements ListenableFutureCallback<SendResult<String, String>> {

		@Override
		public void onSuccess(SendResult<String, String> result) {
			log.debug("Sent message=[{}] with offset=[{}]",  result.getProducerRecord() , result.getRecordMetadata().offset());
		}

		@Override
		public void onFailure(Throwable ex) {
			log.error("Unable to send message due to : {}",  ex.getMessage());
		}
	}
}