package in.ayush.swasthyapath.service;

import in.ayush.swasthyapath.model.FoodData;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class MealPlanner {

    private double targetCarb;
    private double targetProtein;
    private double targetFat;
    private double minTargetC;
    private double minTargetP;
    private double minTargetF;
    private final double carbTolerance = 0.3; // 30 percent.
    private final double proteinTolerance = 0.1; // 10 percent.
    private final double fatTolerance = 0.1; // 10 percent.

    public void setTarget(Map<String, double[]> distribution, String mealType) {

        double totalCarb = 0d;
        double totalProtein = 0d;
        double totalFat = 0d;


        switch (mealType) {
            case "Breakfast":
                totalCarb = distribution.get("CARB")[0];
                totalProtein = distribution.get("PROTEIN")[0];
                totalFat = distribution.get("FAT")[0];
                targetCarb = totalCarb * (1 + carbTolerance);
                targetProtein = totalProtein * (1 + proteinTolerance);
                targetFat = totalFat * (1 + fatTolerance);
                minTargetC = totalCarb * (1 - carbTolerance);
                minTargetP = totalProtein * (1 - proteinTolerance);
                minTargetF = totalFat * (1 - fatTolerance);
                break;
            case "Lunch":
                totalCarb = distribution.get("CARB")[1];
                totalProtein = distribution.get("PROTEIN")[1];
                totalFat = distribution.get("FAT")[1];
                targetCarb = totalCarb * (1 + carbTolerance);
                targetProtein = totalProtein * (1 + proteinTolerance);
                targetFat = totalFat * (1 + fatTolerance);
                minTargetC = totalCarb * (1 - carbTolerance);
                minTargetP = totalProtein * (1 - proteinTolerance);
                minTargetF = totalFat * (1 - fatTolerance);
                break;
            case "Dinner":
                totalCarb = distribution.get("CARB")[2];
                totalProtein = distribution.get("PROTEIN")[2];
                totalFat = distribution.get("FAT")[2];
                targetCarb = totalCarb * (1 + carbTolerance);
                targetProtein = totalProtein * (1 + proteinTolerance);
                targetFat = totalFat * (1 + fatTolerance);
                minTargetC = totalCarb * (1 - carbTolerance);
                minTargetP = totalProtein * (1 - proteinTolerance);
                minTargetF = totalFat * (1 - fatTolerance);
                break;
            default:
                throw new IllegalArgumentException("The mealtype won't know: "+ mealType);
        }
        minTargetC = targetCarb - 10;
        minTargetP = targetProtein - 0.9;
        minTargetF = targetFat - 0.9;
    }

    public List<FoodData> findPair(List<FoodData> foodDataList) {
        // Now we need to find the pair.
        for (int i=0; i < foodDataList.size(); i++) {

            FoodData currentFoodData = foodDataList.get(i);

            for (int j=i+1; j<foodDataList.size(); j++) {

                FoodData comparableFoodData = foodDataList.get(j);

                if ( isInRange(minTargetC, targetCarb, (currentFoodData.getCarbohydrates() + comparableFoodData.getCarbohydrates())) &&
                        isInRange(minTargetP, targetProtein, (currentFoodData.getProtein() + comparableFoodData.getProtein())) &&
                        isInRange(minTargetF, targetFat, (currentFoodData.getFats() + comparableFoodData.getFats())) ){
                    return List.of(currentFoodData, comparableFoodData);
                }

            }
        }
        return Collections.emptyList();
    }

    public List<FoodData> findCombination(List<FoodData> foodDataList) {
        // Now we need to find the pair.
        for (int i=0; i < foodDataList.size(); i++) {
            FoodData currentFoodData = foodDataList.get(i);
            for (int j=i+1; j<foodDataList.size(); j++) {
                FoodData comparableFoodData1 = foodDataList.get(j);
                for (int k=j+1; k<foodDataList.size(); k++) {

                    FoodData comparableFoodData2 = foodDataList.get(k);

                    if ( isInRange(minTargetC, targetCarb, (currentFoodData.getCarbohydrates() + comparableFoodData1.getCarbohydrates() + comparableFoodData2.getCarbohydrates())) &&
                            isInRange(minTargetP, targetProtein, (currentFoodData.getProtein() + comparableFoodData1.getProtein() + comparableFoodData2.getProtein())) &&
                            isInRange(minTargetF, targetFat, (currentFoodData.getFats() + comparableFoodData1.getFats() + comparableFoodData2.getFats())) ){
                        return List.of(currentFoodData, comparableFoodData1, comparableFoodData2);
                    }

                }
            }
        }
        return Collections.emptyList();
    }

    private boolean isInRange(double minValue, double maxValue, double actualValue) {
        if (actualValue >= minValue && actualValue <= maxValue) {
            return true;
        }
        return false;
    }

}