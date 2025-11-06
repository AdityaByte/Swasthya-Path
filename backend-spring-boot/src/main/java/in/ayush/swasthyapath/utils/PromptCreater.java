package in.ayush.swasthyapath.utils;

import in.ayush.swasthyapath.model.Patient;

import java.util.List;
import java.util.Map;

public class PromptCreater {

    public static String createPrompt(Patient patient) {

        return """
    You are an expert Ayurvedic diet planner and nutritionist.
    Based on the following patient details, generate a **personalized daily diet plan** in strict JSON format.

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

    STRICT INSTRUCTIONS:
    1. Create ONLY %d meals according to meal frequency (e.g., if 3 → breakfast, lunch, dinner).
    2. Include meal names based on frequency (e.g., if 5 → Morning, Breakfast, Lunch, Evening, Dinner).
    3. Respect preferred food genre:
       - If "Veg" → strictly vegetarian items only.
       - If "Non-Veg" → include meat, eggs, or fish.
       - If "Mixed" → a balanced mix of veg and non-veg foods.
    4. Ensure meals reflect Ayurvedic balance for prakriti, vikruti, guna, and rasa.
       - Focus on the tastes mentioned in 'Rasa (preferred tastes)' (e.g., sweet, bitter, pungent).
    5. Each meal must include:
       - items (comma-separated list of foods)
       - calories (approximation)
       - rasa (dominant taste)
       - property (Ayurvedic nature, e.g., cooling, warming, grounding)
    6. Ensure daily plan matches the macro nutrient requirements provided.
    7. Output MUST be strictly valid JSON matching this structure:
        {
          "dayPlan": [
            {"meal": "Breakfast", "items": "...", "calories": 0.0, "rasa": "...", "property": "..."},
            {"meal": "Lunch", "items": "...", "calories": 0.0, "rasa": "...", "property": "..."},
            ...
          ],
          "guidelines": [
            "Tip 1",
            "Tip 2",
            ...
          ]
        }
    8. Do NOT add explanations, notes, or text outside JSON.

    Example Meal Frequency Mapping:
    - 3 → Breakfast, Lunch, Dinner
    - 4 → Morning, Lunch, Evening Snack, Dinner
    - 5 → Morning, Breakfast, Lunch, Evening, Dinner
    - 6 → Morning, Breakfast, Mid-morning Snack, Lunch, Evening, Dinner

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
                macroMapToString(patient.getMacroNutrient()),
                patient.getMealFrequency() // for rule 1 formatting
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
