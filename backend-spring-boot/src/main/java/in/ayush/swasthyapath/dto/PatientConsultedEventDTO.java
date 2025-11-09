package in.ayush.swasthyapath.dto;

import in.ayush.swasthyapath.enums.Dosha;
import in.ayush.swasthyapath.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientConsultedEventDTO {
    private String id;
    private String name;
    private Dosha prakruti;
    private Dosha vikruti;
    private byte age;
    private Gender gender;
    private float height, weight;
    private AyurvedaAssessment assessment;
}
