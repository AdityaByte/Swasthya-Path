package in.ayush.swasthyapath.utils;

import in.ayush.swasthyapath.dto.BasicInformation;
import in.ayush.swasthyapath.dto.HealthInformation;
import in.ayush.swasthyapath.dto.PatientInformation;
import in.ayush.swasthyapath.enums.Dosha;

import java.util.List;
import java.util.Map;

public class PromptCreater {

    public static String createPrompt(PatientInformation patientInformation, Map<String, Double> macroNutrient, Dosha prakruti, Dosha vikruti) {

        BasicInformation basicInformation = patientInformation.getBasicInfo();
        HealthInformation healthInformation = patientInformation.getHealthInfo();

        return """
        You are an expert Ayurvedic diet planner. Based on the following patient details, generate a personalized daily diet plan.

        Patient Information:
        - Name: %s
        - Age: %d
        - Gender: %s
        - Height: %.1f cm
        - Weight: %.1f kg
        - Activity level: %s
        - Health issues: %s
        - Allergies: %s
        - Meal frequency: %d times/day
        - Sleep pattern: %s, %.1f hrs
        - Water intake: %f times/day
        - Preferred food genre: %s
        - Prakriti (constitution): %s
        - Vikruti (imbalance): %s
        - Digestion strength: %s
        - Preferred tastes: %s
        - Macro Nutrient Requirement: %s

        Instructions:
        1. Use Ayurvedic principles of prakriti, vikruti, dosha, and guna balancing.
        2. Exclude food items that conflict with allergies or preferred food genre.
        3. Suggest meals according to mealFrequency (breakfast, lunch, dinner, snacks if applicable).
        4. Keep meals simple, practical, and aligned with Indian dietary context.
        5. Output MUST be strictly in JSON format, without any extra text.
        6. The JSON should contain ONLY these fields:
        {
          "dayPlan": {
            "breakfast": "...",
            "lunch": "...",
            "dinner": "...",
            "snacks": "..."
          },
          "guidelines": ["..."]
        }
        7. Do NOT add extra fields, comments, explanations, or notes outside this JSON.
        """.formatted(
                basicInformation.getName(),
                basicInformation.getAge(),
                basicInformation.getGender(),
                basicInformation.getHeight(),
                basicInformation.getWeight(),
                basicInformation.getActivityLevel(),
                listToString(healthInformation.getHealthIssues()),
                listToString(healthInformation.getAllergies()),
                basicInformation.getMealFrequency(),
                basicInformation.getSleepingSchedule(),
                basicInformation.getHoursOfSleep(),
                basicInformation.getWaterIntake(),
                basicInformation.getPreferredFoodGenre(),
                prakruti,
                vikruti,
                patientInformation.getPrakrutiAssessment().getDigestionStrength(),
                listToString(healthInformation.getPreferredTastes()),
                macroMapToString(macroNutrient)
        );
    }

    private static String listToString(List<?> list) {
        return (list == null || list.isEmpty()) ? "None" : String.join(", ", list.stream().map(Object::toString).toList());
    }

    private static String macroMapToString(Map<String, Double> map) {
        if (map == null || map.isEmpty()) return "Not specified";
        return map.entrySet()
                .stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .reduce((a, b) -> a + ", " + b)
                .orElse("Not specified");
    }
}
