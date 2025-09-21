package in.ayush.swasthyapath.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Utility {

    public Map<String, double[]> distinguishNutrientMealCapacity(Map<String, Double> macroNutrients, byte mealFrequency) {
        double targetCarb = macroNutrients.get("CARB");
        double targetProtein = macroNutrients.get("PROTEIN");
        double targetFat = macroNutrients.get("FAT");

        Map<String, double[]> distribution = new HashMap<>();

        double[] carbDist;
        double[] proteinDist;
        double[] fatDist;

        if (mealFrequency == 2) {
            carbDist = new double[]{
                    targetCarb / 2,
                    targetCarb / 2
            };
            proteinDist = new double[] {
                    targetProtein / 2,
                    targetProtein / 2
            };
            fatDist = new double[] {
                    targetFat / 2,
                    targetFat / 2
            };
        } else if (mealFrequency == 3) {
            carbDist = new double[] {
                    targetCarb * 0.2,
                    targetCarb * 0.4,
                    targetCarb * 0.4,
            };

            proteinDist = new double[] {
                    targetProtein * 0.2,
                    targetProtein * 0.4,
                    targetProtein * 0.4,
            };

            fatDist = new double[] {
                    targetFat * 0.2,
                    targetFat * 0.4,
                    targetFat * 0.4,
            };
        } else {
            throw new IllegalArgumentException("Meal frequency not supported: " + mealFrequency);
        }

        distribution.put("CARB", carbDist);
        distribution.put("PROTEIN", proteinDist);
        distribution.put("FAT", fatDist);

        return distribution;
    }
}
