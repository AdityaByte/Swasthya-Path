package in.ayush.swasthyapath.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AyurvedaAssessment {

    private BasicAssessment basicAssessment;
    private HealthAssessment healthAssessment;
    private PrakrutiAssessment prakrutiAssessment;
    private VikrutiAssessment vikrutiAssessment;
}

