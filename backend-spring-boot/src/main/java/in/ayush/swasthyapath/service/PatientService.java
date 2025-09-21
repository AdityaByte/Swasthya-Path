package in.ayush.swasthyapath.service;

import in.ayush.swasthyapath.dto.PatientInformation;
import in.ayush.swasthyapath.dto.ResponseData;
import in.ayush.swasthyapath.enums.Dosha;
import in.ayush.swasthyapath.model.FoodData;
import in.ayush.swasthyapath.pojo.DoshaPercent;
import in.ayush.swasthyapath.repository.DietRepository;
import in.ayush.swasthyapath.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final DietRepository repository;
    private final Utility utility;
    private final MealPlanner mealPlanner;

    private static final Map<Dosha, DoshaPercent> DOSHA_MAP = Map.of(
            Dosha.VATA, new DoshaPercent(0.525, 0.225, 0.25),
            Dosha.PITTA, new DoshaPercent(0.45, 0.275, 0.275),
            Dosha.KAPHA, new DoshaPercent(0.475, 0.275, 0.25)
    );


    public Map<String, List<FoodData>> prepareDiet(PatientInformation patientInformation) {
        double patientBMR = calculateBMR(patientInformation);
        Map<String, Double> macroNutrient = getMacroNutrient(patientInformation.getPrakriti(), patientBMR);
        // Now we have to fetch the meal based on the user preferences.
        List<FoodData> foodDataList = repository
                .fetchMeal(
                        macroNutrient,
                        patientInformation.getMealFrequency(),
                        patientInformation.getPreferredFoodGenre(),
                        patientInformation.getAllergies());

        Map<String, List<FoodData>> result = new HashMap<>();

        if (!foodDataList.isEmpty()) {
            // We do have to prepare the meal so that the calories intake of the day must be fulfilled.]

            List<FoodData> tempFoodDataListBreakFast = new ArrayList<>();
            List<FoodData> tempFoodDataListLunch = new ArrayList<>();
            List<FoodData> tempFoodDataListDinner = new ArrayList<>();

            for (FoodData foodData : foodDataList) {

                if (foodData.getMealType().contains("Breakfast")) {
                    tempFoodDataListBreakFast.add(foodData);
                } else if (foodData.getMealType().contains("Lunch")) {
                    tempFoodDataListLunch.add(foodData);
                } else if (foodData.getMealType().contains("Dinner")) {
                    tempFoodDataListDinner.add(foodData);
                } else {
                    System.out.println("I haven't know the food-data meal type " + foodData.getMealType());
                }
            }

            // OK.

            Map<String, double[]> distribution = utility.distinguishNutrientMealCapacity(macroNutrient, patientInformation.getMealFrequency());


            mealPlanner.setTarget(distribution, "Breakfast");
            List<FoodData> findPairBreakfast = mealPlanner.findPair(tempFoodDataListBreakFast);
            if (findPairBreakfast.isEmpty()) {
                result.put("Breakfast", mealPlanner.findCombination(tempFoodDataListBreakFast));
            } else {
                result.put("Breakfast", findPairBreakfast);
            }

            mealPlanner.setTarget(distribution, "Lunch");
            List<FoodData> findPairLunch = mealPlanner.findPair(tempFoodDataListLunch);
            if (findPairLunch.isEmpty()) {
                result.put("Lunch", mealPlanner.findCombination(tempFoodDataListLunch));
            } else {
                result.put("Lunch", findPairLunch);
            }

            mealPlanner.setTarget(distribution, "Dinner");
            List<FoodData> findPairDinner = mealPlanner.findPair(tempFoodDataListDinner);
            if (findPairDinner.isEmpty()) {
                result.put("Dinner", mealPlanner.findCombination(tempFoodDataListDinner));
            } else {
                result.put("Dinner", findPairDinner);
            }
        }
        return result;
    }

    public ResponseData getInformation(PatientInformation patientInformation) {
        double bmr = calculateBMR(patientInformation);
        var macroNutrient = getMacroNutrient(patientInformation.getPrakriti(), bmr);
        return new ResponseData(bmr, macroNutrient);
    }

    private double calculateBMR(PatientInformation patientInformation) {
        // Now we have to calculate the BMR.
        // BMR - Basal Metabolic Rate
        // Through this we basically calculate the number of calories we want for properly functioning the body.

        double bmr = 0;

        switch (patientInformation.getGender()){

            case MALE ->
                bmr = 66 + (13.7 * patientInformation.getWeight())
                        + (5 * patientInformation.getHeight())
                        - (6.8 * patientInformation.getAge());

            case FEMALE ->
                bmr = 655 + (9.6 * patientInformation.getWeight())
                        + (1.8 * patientInformation.getHeight())
                        - (4.7 * patientInformation.getAge());

            case OTHER ->
                bmr = 10 * patientInformation.getWeight()
                        + 6.25 * patientInformation.getHeight()
                        - 5 * patientInformation.getAge();
        }

        double activityMultiplier = switch (patientInformation.getActivityLevel()) {
            case SEDENTARY -> 1.2;
            case MODERATE -> 1.5;
            case ACTIVE -> 1.75;
        };

        return bmr * activityMultiplier;
    }

    // Now we need to calculate the micro-nutrient.
    // Just do it doesn't think like it's not the best way of doing that although after some you'll know.
    // Get macronutrient on the basis of the persons prakriti.
    private Map<String, Double> getMacroNutrient(Dosha prakriti, double bmr) {
        DoshaPercent doshaPercent = DOSHA_MAP.get(prakriti);
        return Map.of(
                "CARB", (doshaPercent.getCarbPercent() * bmr) / 4,
                "PROTEIN", (doshaPercent.getProteinPercent() * bmr) / 4,
                "FAT", (doshaPercent.getFatPercent() * bmr) / 9
        );
    }
}
