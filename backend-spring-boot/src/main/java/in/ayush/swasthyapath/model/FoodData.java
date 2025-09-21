package in.ayush.swasthyapath.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "foot-data")
public class FoodData {

    @Id
    private String id;

    private String ayurvedicCombo;
    private String mealType;
    private String name;
    @Field(name = "vegNonVeg")
    private String foodGenre;

    private double calcium;
    private double calories;
    private double carbohydrates;
    private double fats;
    private double fibre;
//    private double folate;
    private double freeSugar;
    private double iron;
    private double protein;
    private double sodium;
//    private double vitaminC;

    private List<String> allergens;
    private String category;
    private boolean isHealthy;
    private List<String> tags;

}
