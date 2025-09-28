package in.ayush.swasthyapath.utils;

import in.ayush.swasthyapath.model.Patient;

import java.util.List;
import java.util.Map;

public class PromptCreater {

    public static String createPrompt(Patient patient) {

        return """
        You are an expert Ayurvedic diet planner. Based on the following patient details, generate a personalized daily diet plan.

        Patient Information:
        - Name: %s
        - Age: %d
        - Gender: %s
        - Height: %.1f cm
        - Weight: %.1f kg
        - Activity level: %s
        - Preferred food genre: %s
        - Meal frequency: %d times/day
        - Sleep pattern: %s
        - Water intake: %.1f litres/day
        - Prakriti (constitution): %s
        - Vikruti (imbalance): %s
        - Guna: %s
        - Rasa (preferred tastes): %s
        - Agni (digestion strength): %s
        - Macro Nutrient Requirement: %s

        Instructions:
        1. Use Ayurvedic principles of prakriti, vikruti, dosha, guna, rasa, and agni balancing.
        2. Suggest meals according to meal frequency (breakfast, lunch, dinner, snacks if applicable).
        3. Exclude foods that conflict with the preferred food genre.
        4. Keep meals simple, practical, and aligned with Indian dietary context.
        5. Output MUST be strictly in JSON format compatible with the HealthResponse DTO:
        {
          "dayPlan": [
            {"meal": "Morning", "items": "...", "calories": 0.0, "rasa": "...", "property": "..."},
            {"meal": "Breakfast", "items": "...", "calories": 0.0, "rasa": "...", "property": "..."},
            {"meal": "Lunch", "items": "...", "calories": 0.0, "rasa": "...", "property": "..."},
            {"meal": "Evening", "items": "...", "calories": 0.0, "rasa": "...", "property": "..."},
            {"meal": "Dinner", "items": "...", "calories": 0.0, "rasa": "...", "property": "..."}
          ],
          "guidelines": ["..."]
        }
        6. Do NOT add any extra fields, comments, explanations, or notes outside this JSON.
        7. Ensure 'rasa' and 'property' are separate fields for each meal.
        """.formatted(
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getHeight(),
                patient.getWeight(),
                patient.getActivityLevel(),
                patient.getPreferredFoodGenre(),
                patient.getMealFrequency(),
                patient.getSleepingSchedule(),
                patient.getWaterIntake(),
                patient.getPrakruti(),
                patient.getVikruti(),
                patient.getGuna(),
                listToString(patient.getRasa()),
                patient.getAgni(),
                macroMapToString(patient.getMacroNutrient())
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
