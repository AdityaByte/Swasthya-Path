package in.ayush.swasthyapath.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("brevo")
public class MailServiceBrevoImpl implements MailService {

    @Value("${brevo.api.key}")
    private String BREVO_API_KEY;
    @Value("${brevo.email}")
    private String BREVO_EMAIL;
    @Value("${brevo.url}")
    private String BREVO_URL;
    private final RestTemplate restTemplate;

    @Override
    public boolean sendMail(String to, String subject, String content) {
        Map<String, Object> body = new HashMap<>();
        body.put("sender", Map.of(
                "email", BREVO_EMAIL,
                "name", "Swasthya Path"));
        body.put("to", List.of(Map.of("email", to)));
        body.put("subject", subject);
        body.put("htmlContent", content);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", BREVO_API_KEY);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(BREVO_URL, HttpMethod.POST, request, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to send email through brevo, {}", response.getBody());
            return false;
        }

        log.info("Email is successfully sent to {}", to);
        return true;
    }
}
