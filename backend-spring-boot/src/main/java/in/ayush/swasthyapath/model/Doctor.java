package in.ayush.swasthyapath.model;

import in.ayush.swasthyapath.enums.Dosha;
import in.ayush.swasthyapath.enums.Gender;
import in.ayush.swasthyapath.enums.UserStatus;
import in.ayush.swasthyapath.event.model.DoctorConsultedEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Doctor Model:
 * These are the basic information that the doctor should have for registration.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "doctor_data")
public class Doctor {

    @Id
    private String id;

    // Basic Information.
    private String name;
    private Gender gender;
    private Date dob;
    private byte age;
    private String email;
    private String phoneNumber;
    private String password;
    private UserStatus status;

    // Professional Details.
    private String registrationNumber; // AYUSH, Govt Registration No.
    private String qualification; // DEG.
    private float experienceYears;

    // Ayurvedic Specialization.
    private String specialization;
    private Dosha doshaExpertise;

    // This field track the records of the patients whose data need to be consulted.
    private List<DoctorConsultedEvent> pendingPatients;

    // This field tracks of the record the patients who are consulted.
    private List<DoctorConsultedEvent> consultedPatients;
}