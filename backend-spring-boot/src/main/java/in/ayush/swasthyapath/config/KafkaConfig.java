package in.ayush.swasthyapath.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("kafka")
public class KafkaConfig {

    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;

    @Bean
    public NewTopic newTopic() {
        return new NewTopic(TOPIC_NAME, 1, (short) 1);
    }
}
