package es.davidmoros.poc.kafka.subscribe;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.debug("Application runned with arguments: {}", Arrays.stream(args.getSourceArgs()).collect(Collectors.joining(" ")));
	}
	
	@KafkaListener(topicPattern = "${kafka.topic}", groupId = "${kafka.group}")
	public void auditTopic(String message) {
		log.info("Recieved Message: {}", message);
	}	
}
