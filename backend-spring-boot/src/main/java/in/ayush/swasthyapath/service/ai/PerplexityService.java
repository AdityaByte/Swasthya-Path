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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PerplexityService {

    private static final Logger log = LoggerFactory.getLogger(PerplexityService.class);
    @Value("${perplexity.ai.api.key}")
    private String PERPLEXITY_API_KEY;
    @Value("${perplexity.ai.api.base-url}")
    private String PERPLEXITY_BASE_URL;
    @Value("${perplexity.ai.api.model}")
    private String PERPLEXITY_MODEL;
    @Value("${perplexity.ai.api.completions-path}")
    private String PERPLEXITY_COMPLETIONS_PATH;
    private final RestTemplate restTemplate;

    public String generateDiet(String prompt) {

        String url = UriComponentsBuilder
                .fromHttpUrl(PERPLEXITY_BASE_URL)
                .path(PERPLEXITY_COMPLETIONS_PATH)
                .toUriString();

        Map<String, Object> requestBody = Map.of(
                "model", PERPLEXITY_MODEL,
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt
                        )
                ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(PERPLEXITY_API_KEY);

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("Failed to generate diet by perplexity API {}", response);
                return null;
            }

            // Extracting the actual content.
            JsonNode root = new ObjectMapper().readTree(response.getBody());

            return root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

        } catch (Exception e) {
            log.error("Failed to make the post request to perplexity {}", e.getMessage());
            return null;
        }
    }

}
