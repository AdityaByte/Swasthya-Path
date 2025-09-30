package in.ayush.swasthyapath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class SwasthyaPathApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwasthyaPathApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(5);
    }
}