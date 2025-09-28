package in.ayush.swasthyapath.dto;

import in.ayush.swasthyapath.enums.Dosha;
import in.ayush.swasthyapath.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private Patient patient;
    private HealthResponse healthResponse;
}
