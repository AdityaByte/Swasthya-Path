package in.ayush.swasthyapath.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class DoctorDTO {

    // Basic Information.
    private String name;
    private Gender gender;
    private Date dob;
    private String email;
    @JsonAlias("phone_number")
    private String phoneNumber;
    private String password;

    // Professional Details.
    @JsonAlias("registration_number")
    private String registrationNumber; // AYUSH, Govt Registration No.
    private String qualification; // DEG.
    @JsonAlias("experience")
    private float experienceYears;

    // Ayurvedic Specialization.
    private String specialization;
    @JsonAlias("dosha_expertise")
    private Dosha doshaExpertise;

}
