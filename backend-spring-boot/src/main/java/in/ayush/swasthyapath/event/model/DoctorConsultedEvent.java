package in.ayush.swasthyapath.event.model;

import in.ayush.swasthyapath.enums.ActivityLevel;
import in.ayush.swasthyapath.enums.Agni;
import in.ayush.swasthyapath.enums.Dosha;
import in.ayush.swasthyapath.enums.SleepingSchedule;
import lombok.*;
import org.springframework.context.annotation.Profile;

import java.util.List;

@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Profile("kafka")
public class DoctorConsultedEvent {
    private String patientId;
    private String patientName;
    private Dosha prakruti;
    private Dosha vikruti;
    private Agni agni;
    private String guna;
    private List<String> rasa;
    private ActivityLevel activityLevel;
    private SleepingSchedule sleepingSchedule;
    private byte mealFrequency;
}
