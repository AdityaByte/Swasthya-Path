package in.ayush.swasthyapath.model;

import in.ayush.swasthyapath.enums.Dosha;
import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Doctor Model:
 * These are the basic information that the doctor should have for registration.
 */

@Data
@Document(collection = "doctor_data")
public class Doctor {

    @Id
    private String id;

    // Basic Information.
    private String name;
    private String email;
    private String phoneNumber;
    private String password;

    // Professional Details.
    private String registrationNumber; // AYUSH, Govt Registration No.
    private String qualification; // DEG.
    private float experienceYears;

    // Ayurvedic Specialization.
    private String specialization;
    private Dosha doshaExpertise;
}
