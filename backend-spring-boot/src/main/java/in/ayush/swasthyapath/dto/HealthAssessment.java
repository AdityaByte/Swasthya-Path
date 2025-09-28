package in.ayush.swasthyapath.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.ayush.swasthyapath.enums.Agni;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthAssessment {

    private List<String> healthIssues;
    private List<String> allergies;
    private List<String> preferredTastes;
    private Agni agni;
}
