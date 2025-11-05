package in.ayush.swasthyapath.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.mail") // Just in case if it didn't find sometimes.
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }

}
