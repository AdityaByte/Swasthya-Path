package in.ayush.swasthyapath.dto;

import in.ayush.swasthyapath.enums.ActivityLevel;
import in.ayush.swasthyapath.enums.FoodGenre;
import in.ayush.swasthyapath.enums.Gender;
import in.ayush.swasthyapath.enums.SleepingSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicInformation {

    private String name;
    private byte age;
    private Gender gender;
    private float height;
    private float weight;
    private ActivityLevel activityLevel;
    private byte mealFrequency;
    private SleepingSchedule sleepingSchedule;
    private float hoursOfSleep;
    private float waterIntake;
    private FoodGenre preferredFoodGenre;
}
