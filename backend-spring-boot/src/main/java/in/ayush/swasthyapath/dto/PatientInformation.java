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

    private String name;
    private byte age;
    private Gender gender;
    private float height; // In cm
    private float weight; // In kg. Following indian standards.
    private ActivityLevel activityLevel;
    private List<String> healthIssue; // Here we take a list of string although if the patient has no health issue then it will be blank.
    // More likely if the patient allergic to some food we won't prescribe it.
    private List<String> allergies;
    private byte mealFrequency; // 2/3/4 times a day.
    private String sleepingSchedule; // Early sleep, late sleep.
    private float hoursOfSleep;
    private byte waterIntake; // Water Intake how many times a day.
    private String preferredFoodGenre; // Veg / Non-Veg

    // Ayurveda assessment.
    private Dosha prakriti;
    private Dosha vikruti; // Current imbalance.
    private DigestionStrength digestionStrength;
    private List<Taste> preferredTastes;
}

