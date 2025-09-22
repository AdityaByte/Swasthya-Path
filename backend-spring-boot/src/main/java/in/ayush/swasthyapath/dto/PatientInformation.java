package in.ayush.swasthyapath.dto;

import in.ayush.swasthyapath.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientInformation {

    private BasicInformation basicInfo;
    private HealthInformation healthInfo;
    private PrakrutiAssessment prakrutiAssessment;
    private VikrutiAssessment vikrutiAssessment;
}

