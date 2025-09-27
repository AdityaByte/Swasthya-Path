package in.ayush.swasthyapath.model;

import in.ayush.swasthyapath.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "patient_data")
public class Patient {

    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private Gender gender;
    private float height;
    private float weight;
    private boolean assessmentDone; // If true then dashboard has been shown.
    private FoodGenre preferredFoodGenre;
    private ActivityLevel activityLevel;
    private float waterIntake; // In litres/per-day.
    private SleepingSchedule sleepingSchedule; // Early / Late.


    // Calories that do needed.
    private double calorie;

    // BMR
    private double bmr;

    // Prakruti
    private Dosha prakruti;

    // Vikruti
    private Dosha vikruti;

    private Guna guna; // Quality of food: hot, cold, mild etc.
    private Rasa rasa; // Taste
    private Agni agni; // Digestion strength.

    public boolean getAssessmentDone() {
        return this.assessmentDone;
    }
}
