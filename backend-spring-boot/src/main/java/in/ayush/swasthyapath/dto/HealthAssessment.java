package in.ayush.swasthyapath.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthInformation {

    private List<String> healthIssues;
    private List<String> allergies;
    private List<String> preferredTastes;

}
