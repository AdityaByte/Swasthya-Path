package in.ayush.swasthyapath.dto;

import in.ayush.swasthyapath.enums.Dosha;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData {
    private double bmr;
    private Map<String, Double> macroNutrient;
    private Dosha prakruti;
    private Dosha vikruti;
}
