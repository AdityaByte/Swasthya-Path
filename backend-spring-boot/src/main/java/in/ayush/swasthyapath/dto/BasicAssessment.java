package in.ayush.swasthyapath.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.ayush.swasthyapath.enums.ActivityLevel;
import in.ayush.swasthyapath.enums.FoodGenre;
import in.ayush.swasthyapath.enums.Gender;
import in.ayush.swasthyapath.enums.SleepingSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicAssessment {

    private ActivityLevel activityLevel;
    private float waterIntake;
    private byte mealFrequency;
    private SleepingSchedule sleepingSchedule;
    private float hoursOfSleep;
    private FoodGenre preferredFoodGenre;
}
