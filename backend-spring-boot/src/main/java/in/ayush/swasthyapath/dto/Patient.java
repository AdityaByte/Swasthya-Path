package in.ayush.swasthyapath.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.ayush.swasthyapath.enums.DoctorConsultedStatus;
import in.ayush.swasthyapath.enums.Dosha;
import in.ayush.swasthyapath.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient {

    private String name;
    private String email;
    private String phoneNumber;
    private Date dob;
    private byte age;
    private Gender gender;
    private float weight;
    private float height;
    private String password;
    private Dosha prakruti;
    private Dosha vikruti;
    private boolean assessmentDone;
    private DoctorConsultedStatus consultedStatus;
}
