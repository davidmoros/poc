package es.davidmoros.poc.kafka.publish;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.davidmoros.poc.kafka.publish.service.KafkaPublishService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

	@Value("${kafka.topic}")
	private String topic;
	@Value("${kafka.message}")
	private String message;

	@Autowired
	private KafkaPublishService kafkaPublishService;
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.debug("Application runned with arguments: {}", Arrays.stream(args.getSourceArgs()).collect(Collectors.joining(" ")));

		kafkaPublishService.publish(topic, message);
	}
}
