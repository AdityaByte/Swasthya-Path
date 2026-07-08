package in.ayush.swasthyapath.service.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GroqService {

    private static final Logger log = LoggerFactory.getLogger(GroqService.class);
    @Value("${groq.api.key}")
    private String GROQ_API_KEY;
    @Value("${groq.url}")
    private String GROQ_URL;
    @Value("${groq.model}")
    private String GROQ_MODEL_NAME;
    private final RestTemplate restTemplate;

    public String generateDiet(String prompt) {

        List<Map<String, Object>> messages = List.of(Map.of(
                "role", "user",
                "content", prompt
        ));

        Map<String, Object> requestBody = Map.of(
                "model", GROQ_MODEL_NAME,
                "messages", messages,
                "temperature", 0.7
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(GROQ_API_KEY);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(GROQ_URL, HttpMethod.POST, httpEntity, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Failed to generate diet by Groq API {}", response);
                return null;
            }

            JsonNode root = new ObjectMapper().readTree(response.getBody());
            return root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

        } catch (Exception e) {
            log.error("Failed to make the post request to groq {}", e.getMessage());
            return null;
        }

    }


}
