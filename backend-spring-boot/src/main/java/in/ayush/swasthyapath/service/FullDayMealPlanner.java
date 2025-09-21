package in.ayush.swasthyapath.service;

import in.ayush.swasthyapath.model.FoodData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FullDayMealPlanner {

    private final double targetCarb;
    private final double targetProtein;
    private final double targetFat;
    private final double minTargetC;
    private final double minTargetP;
    private final double minTargetF;

    public FullDayMealPlanner(double tc, double tp, double tf) {
        this.targetCarb = tc;
        this.targetProtein = tp;
        this.targetFat = tf;

        // Use ±10% margin (instead of fixed -10)
        this.minTargetC = targetCarb * 0.9;
        this.minTargetP = targetProtein * 0.9;
        this.minTargetF = targetFat * 0.9;
    }

    public List<List<FoodData>> findDailyCombos(
            List<FoodData> breakfasts,
            List<FoodData> lunches,
            List<FoodData> dinners) {

        List<List<FoodData>> results = new ArrayList<>();

        for (FoodData b : breakfasts) {
            for (FoodData l : lunches) {
                for (FoodData d : dinners) {

                    double totalC = b.getCarbohydrates() + l.getCarbohydrates() + d.getCarbohydrates();
                    double totalP = b.getProtein() + l.getProtein() + d.getProtein();
                    double totalF = b.getFats() + l.getFats() + d.getFats();

                    if (isInRange(minTargetC, targetCarb, totalC) &&
                            isInRange(minTargetP, targetProtein, totalP) &&
                            isInRange(minTargetF, targetFat, totalF)) {

                        results.add(List.of(b, l, d));
                    }
                }
            }
        }

        return results.isEmpty() ? Collections.emptyList() : results;
    }

    private boolean isInRange(double minValue, double maxValue, double actualValue) {
        return actualValue >= minValue && actualValue <= maxValue;
    }
}
