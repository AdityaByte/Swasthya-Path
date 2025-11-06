package in.ayush.swasthyapath.kafka.producer;

import in.ayush.swasthyapath.event.model.DoctorConsultedEvent;
import in.ayush.swasthyapath.event.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("kafka")
public class DoctorConsultedEventProducer implements EventProducer {

    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void produceEvent(DoctorConsultedEvent consultedEvent) {
        log.info("Event received to the event producer sending event");
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(TOPIC_NAME, consultedEvent);
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("Event successfully sent to the doctor.");
            } else {
                log.error("Unable to send the event to the doctor, {}", exception.getMessage());
            }
        });
    }


}
