//package in.ayush.swasthyapath.repository;
//
//import in.ayush.swasthyapath.model.FoodData;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Map;
//
//// Haven't needed this too in this version.
//
//@Repository
//@RequiredArgsConstructor
//public class DietRepository {
//
//    private final MongoTemplate mongoTemplate;
//
//    public List<FoodData> fetchMeal(Map<String, Double> macroNutrient, byte mealFrequency, String foodGenre, List<String> allergons) {
//
//        double carbPerMeal = macroNutrient.get("CARB") / mealFrequency;
//        double proteinPerMeal = macroNutrient.get("PROTEIN") / mealFrequency;
//        double fatPerMeal = macroNutrient.get("FAT") / mealFrequency;
//
//        Query query = new Query();
//
//        if (mealFrequency == 2){
//            query.addCriteria(Criteria.where("mealType").in("Breakfast", "Dinner"));
//        } else if (mealFrequency == 3) {
//            query.addCriteria(Criteria.where("mealType").in("Breakfast", "Lunch", "Dinner"));
//        }
//
//        query.addCriteria(Criteria.where("vegNonVeg").in(List.of(foodGenre)));
//        query.addCriteria(Criteria.where("carbohydrates").lte(carbPerMeal));
//        query.addCriteria(Criteria.where("protein").lte(proteinPerMeal));
//        query.addCriteria(Criteria.where("fats").lte(fatPerMeal));
//        if (allergons != null && !allergons.isEmpty()) {
//            query.addCriteria(Criteria.where("allergens").not().in(allergons));
//        }
//
//        return mongoTemplate.find(query, FoodData.class);
//    }
//
//}
