package in.ayush.swasthyapath.service;

import in.ayush.swasthyapath.dto.PatientInformation;
import in.ayush.swasthyapath.dto.ResponseData;
import in.ayush.swasthyapath.enums.Dosha;
import in.ayush.swasthyapath.pojo.DoshaPercent;
import in.ayush.swasthyapath.utils.PromptCreater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final GeminiService geminiService;

    private static final Map<Dosha, DoshaPercent> DOSHA_MAP = Map.of(
            Dosha.VATA, new DoshaPercent(0.525, 0.225, 0.25),
            Dosha.PITTA, new DoshaPercent(0.45, 0.275, 0.275),
            Dosha.KAPHA, new DoshaPercent(0.475, 0.275, 0.25)
    );


    public String prepareDiet(PatientInformation patientInformation) {
        double patientBMR = calculateBMR(patientInformation);
        Map<String, Double> macroNutrient = getMacroNutrient(patientInformation.getPrakriti(), patientBMR);

        // Now we are calling the gemini service.
        String prompt = PromptCreater.createPrompt(patientInformation, macroNutrient);
        return geminiService.generateDiet(prompt);
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
