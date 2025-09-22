package in.ayush.swasthyapath.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VikrutiAssessment {

    private String currentConcerns;
    private String currentEnergy;
    private String currentSleep;
    private String digestionToday;
}
