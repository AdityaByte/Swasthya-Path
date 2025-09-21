package in.ayush.swasthyapath.utils;

import in.ayush.swasthyapath.dto.PatientInformation;

import java.util.List;
import java.util.Map;

public class PromptCreater {

    public static String createPrompt(PatientInformation patientInformation, Map<String, Double> macroNutrient) {
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
                - Water intake: %d times/day
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
                5. Provide structured output in JSON format:
                {
                  "dayPlan": {
                    "breakfast": "...",
                    "lunch": "...",
                    "dinner": "...",
                    "snacks": "..."
                  },
                  "guidelines": ["..."]
                }
                """.formatted(
                patientInformation.getName(),
                patientInformation.getAge(),
                patientInformation.getGender(),
                patientInformation.getHeight(),
                patientInformation.getWeight(),
                patientInformation.getActivityLevel(),
                listToString(patientInformation.getHealthIssue()),
                listToString(patientInformation.getAllergies()),
                patientInformation.getMealFrequency(),
                patientInformation.getSleepingSchedule(),
                patientInformation.getHoursOfSleep(),
                patientInformation.getWaterIntake(),
                patientInformation.getPreferredFoodGenre(),
                patientInformation.getPrakriti(),
                patientInformation.getVikruti(),
                patientInformation.getDigestionStrength(),
                listToString(patientInformation.getPreferredTastes()),
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
