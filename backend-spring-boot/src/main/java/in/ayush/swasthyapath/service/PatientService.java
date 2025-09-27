//package in.ayush.swasthyapath.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import in.ayush.swasthyapath.dto.BasicAssessment;
//import in.ayush.swasthyapath.dto.HealthResponse;
//import in.ayush.swasthyapath.dto.AyurvedaAssessment;
//import in.ayush.swasthyapath.dto.ResponseData;
//import in.ayush.swasthyapath.enums.Dosha;
//import in.ayush.swasthyapath.pojo.DoshaPercent;
//import in.ayush.swasthyapath.utils.FilterResponse;
//import in.ayush.swasthyapath.utils.PromptCreater;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class PatientService {
//
//    private final GeminiService geminiService;
//    private final DoshaService doshaService;
//    private final ObjectMapper objectMapper;
//    private final PdfService pdfService;
//
//    private static final Map<Dosha, DoshaPercent> DOSHA_MAP = Map.of(
//            Dosha.VATA, new DoshaPercent(0.525, 0.225, 0.25),
//            Dosha.PITTA, new DoshaPercent(0.45, 0.275, 0.275),
//            Dosha.KAPHA, new DoshaPercent(0.475, 0.275, 0.25)
//    );
//
//
//    public HealthResponse prepareDiet(AyurvedaAssessment ayurvedaAssessment) {
//
//        try {
//            double bmr = calculateBMR(ayurvedaAssessment.getBasicInfo());
//
//            // Now we need to calculate the prakruti and vikruti of patient.
//            Dosha prakruti = doshaService.calculatePrakruti(ayurvedaAssessment.getPrakrutiAssessment());
//            Dosha vikruti = doshaService.calculateVikruti(ayurvedaAssessment.getVikrutiAssessment());
//
//            // Deciding the macro-nutrients of body through prakruti.
//            Map<String, Double> macroNutrient = getMacroNutrient(prakruti, bmr);
//
//            // Now we are calling the gemini service.
//            String prompt = PromptCreater.createPrompt(ayurvedaAssessment, macroNutrient, prakruti, vikruti);
//
//            String response = geminiService.generateDiet(prompt);
//
//            // Since the gemini gives the response with some characters which gives some error when we map that to object.
//            // so cleaning the response.
//            String cleanedResponse = FilterResponse.filter(response);
//
//            log.info(cleanedResponse);
//
//            HealthResponse healthResponse = objectMapper.readValue(cleanedResponse, HealthResponse.class);
//
//            // Now we have to create the pdf from the response.
//            String fileURL = pdfService.generatePdf(ayurvedaAssessment, healthResponse, "report");
//
//            healthResponse.setPdfURL(fileURL);
//
//            return healthResponse;
//
//        } catch (Exception exception) {
//            log.error(exception.getMessage());
//            exception.printStackTrace();
//             return null;
//        }
//    }
//
//    public ResponseData getInformation(AyurvedaAssessment ayurvedaAssessment) {
//        double bmr = calculateBMR(ayurvedaAssessment.getBasicInfo());
//        Dosha prakruti = doshaService.calculatePrakruti(ayurvedaAssessment.getPrakrutiAssessment());
//        Dosha vikruti = doshaService.calculateVikruti(ayurvedaAssessment.getVikrutiAssessment());
//        var macroNutrient = getMacroNutrient(prakruti, bmr);
//        return new ResponseData(bmr, macroNutrient, prakruti, vikruti);
//    }
//
//    private double calculateBMR(BasicAssessment basicAssessment) {
//        // Now we have to calculate the BMR.
//        // BMR - Basal Metabolic Rate
//        // Through this we basically calculate the number of calories we want for properly functioning the body.
//
//        double bmr = 0;
//
//        switch (basicAssessment.getGender()){
//
//            case MALE ->
//                bmr = 66 + (13.7 * basicAssessment.getWeight())
//                        + (5 * basicAssessment.getHeight())
//                        - (6.8 * basicAssessment.getAge());
//
//            case FEMALE ->
//                bmr = 655 + (9.6 * basicAssessment.getWeight())
//                        + (1.8 * basicAssessment.getHeight())
//                        - (4.7 * basicAssessment.getAge());
//
//            case OTHER ->
//                bmr = 10 * basicAssessment.getWeight()
//                        + 6.25 * basicAssessment.getHeight()
//                        - 5 * basicAssessment.getAge();
//        }
//
//        double activityMultiplier = switch (basicAssessment.getActivityLevel()) {
//            case SEDENTARY -> 1.2;
//            case MODERATE -> 1.5;
//            case ACTIVE -> 1.75;
//        };
//
//        return bmr * activityMultiplier;
//    }
//
//    // Now we need to calculate the micro-nutrient.
//    // Just do it doesn't think like it's not the best way of doing that although after some you'll know.
//    // Get macronutrient on the basis of the persons prakriti.
//    private Map<String, Double> getMacroNutrient(Dosha prakriti, double bmr) {
//        DoshaPercent doshaPercent = DOSHA_MAP.get(prakriti);
//        return Map.of(
//                "CARB", (doshaPercent.getCarbPercent() * bmr) / 4,
//                "PROTEIN", (doshaPercent.getProteinPercent() * bmr) / 4,
//                "FAT", (doshaPercent.getFatPercent() * bmr) / 9
//        );
//    }
//}
