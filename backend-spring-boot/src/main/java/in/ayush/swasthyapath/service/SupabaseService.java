package in.ayush.swasthyapath.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ayush.swasthyapath.dto.ReportDownload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupabaseService {

    @Value("${supabase.api.url}")
    private String API_URL;
    @Value("${supabase.api.serviceKey}")
    private String API_KEY;
    private final String BUCKET_NAME = "reports";
    private final ObjectMapper objectMapper;

    /* saveReport: It saves the report to supabase since the key is the person's email */
    public void saveReport(String filepath) {
        // Since we have to open the file and save the file to the supabase url by an api call.
        File file = new File(filepath);
        log.info("Filepath at supabase save report function is {}", filepath);
        Path path = Path.of(filepath);
        try {
            byte[] fileBytes = Files.readAllBytes(path);

            if (fileBytes.length == 0) {
                log.info("File is being corrupted no file size");
                return;
            }
            // Now we have to upload the file.
            // Note: kept the API_URL only up to the subdomain not the paths.
            String uploadURL = API_URL + "/storage/v1/object/" + BUCKET_NAME + "/" + file.getName();

            HttpHeaders headers = new HttpHeaders();
            headers.set("apikey", API_KEY);
            headers.set("Authorization", "Bearer " + API_KEY);
            headers.setContentType(MediaType.parseMediaType(Files.probeContentType(path)));

            HttpEntity<byte[]> entity = new HttpEntity<>(fileBytes, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(uploadURL, HttpMethod.PUT, entity, String.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                log.error("File upload failed at supabase: {}", response);
                return;
            }

            log.info("File uploaded successfully at supabase");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                Files.delete(Path.of(filepath));
            } catch (IOException ex) {
                log.error("Failed to delete the file, {}", ex);
            }
        }
    }

    public ResponseEntity<?> downloadReport(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Integer> map = Map.of("expiresIn", (3600)); // Valid till 1 hour.

        HttpEntity<Map<String, Integer>> entity = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        String downloadURL = API_URL + "/storage/v1/object/sign/" + BUCKET_NAME + "/" + filename;
        ResponseEntity<String> response = restTemplate.exchange(downloadURL, HttpMethod.POST, entity, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("File download failed at supabase: {}", response);
            return response;
        }

        // Here we have to map the body to the DTO.
        ReportDownload reportDownload = new ReportDownload();
        try {
            reportDownload = objectMapper.readValue(response.getBody(), ReportDownload.class);
        } catch (Exception ex) {
            log.error("Failed to map the response to the ReportDownload dto, {}", ex.getMessage());
        }

        // Now we have to do something like
        reportDownload.setSignedURL(API_URL + "/storage/v1/" + reportDownload.getSignedURL());

        return ResponseEntity.ok(reportDownload);
    }
}
