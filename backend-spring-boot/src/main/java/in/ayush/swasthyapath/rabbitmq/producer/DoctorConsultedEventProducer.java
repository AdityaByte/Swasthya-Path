package in.ayush.swasthyapath.rabbitmq.producer;

import in.ayush.swasthyapath.event.model.DoctorConsultedEvent;
import in.ayush.swasthyapath.event.producer.EventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("rabbit")
public class DoctorConsultedEventProducer implements EventProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange-name}")
    private String EXCHANGE;
    @Value("${spring.rabbitmq.routing-key}")
    private String ROUTING_KEY;

    @Override
    public void produceEvent(DoctorConsultedEvent consultedEvent) {
        try {
            log.info("Event received to RabbitMQ producer, sending event...");
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, consultedEvent);
            log.info("Event successfully sent to RabbitMQ exchange: {}", EXCHANGE);
        } catch (Exception e) {
            log.error("Unable to send event to RabbitMQ, {}", e.getMessage());
        }
    }
}
