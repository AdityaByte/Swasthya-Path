package in.ayush.swasthyapath.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.ayush.swasthyapath.dto.AyurvedaAssessment;
import in.ayush.swasthyapath.dto.BasicAssessment;
import in.ayush.swasthyapath.dto.HealthResponse;
import in.ayush.swasthyapath.dto.ResponseData;
import in.ayush.swasthyapath.enums.DoctorConsultedStatus;
import in.ayush.swasthyapath.enums.Dosha;
import in.ayush.swasthyapath.event.model.DoctorConsultedEvent;
import in.ayush.swasthyapath.event.producer.EventProducer;
import in.ayush.swasthyapath.model.Patient;
import in.ayush.swasthyapath.pojo.DoshaPercent;
import in.ayush.swasthyapath.repository.PatientRepository;
import in.ayush.swasthyapath.service.ai.GeminiService;
import in.ayush.swasthyapath.service.ai.PerplexityService;
import in.ayush.swasthyapath.utils.FilterResponse;
import in.ayush.swasthyapath.utils.PromptCreater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService {

    private final PatientRepository patientRepository;
    private final DoshaService doshaService;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper;
    private final RedisService redisService;
    private final PdfService pdfService;
    private final SupabaseService supabaseService;
    private final PerplexityService perplexityService;
    private final ExecutorService executorService;
    private final EventProducer eventProducer;

    private static final Map<Dosha, DoshaPercent> DOSHA_MAP = Map.of(
            Dosha.VATA, new DoshaPercent(0.525, 0.225, 0.25),
            Dosha.PITTA, new DoshaPercent(0.45, 0.275, 0.275),
            Dosha.KAPHA, new DoshaPercent(0.475, 0.275, 0.25)
    );

    public ResponseEntity<ResponseData> planDiet(String email) {

        Patient patient = patientRepository.findPatientByEmail(email);
        if (patient == null) {
            return new ResponseEntity<>(new ResponseData(), HttpStatus.UNAUTHORIZED);
        }

        // Before all that we need to generate a kafka event for consulting the patients constitution with doctor.
        if (patient.getDoctorConsultedStatus().equals(DoctorConsultedStatus.PENDING)) {
            // In that case we need to generate the event.
            eventProducer.produceEvent(mapToDoctorConsultedEvent(patient));
        }

        // We do have to map the model patient to the patient dto for data-transfer and for security concern.
        in.ayush.swasthyapath.dto.Patient patientDTO = mapToPatientDTO(patient);

        if (!patient.getAssessmentDone()) {
          return new ResponseEntity<>(ResponseData.
                  builder()
                  .patient(patientDTO)
                  .healthResponse(new HealthResponse())
                  .build(), HttpStatus.OK);
        }

        // Here we have to check the cache.
        ResponseData responseData = redisService.getCachedDietPlan(email);

        if (responseData != null) {
            return ResponseEntity
                    .ok(responseData);
        }

        // Since we do get the patient data now we have to plan the diet.
        String prompt = PromptCreater.createPrompt(patient);

        // Here we need to call the ai service for generating the response.
        // Using ExecutorService so that if anything wrong happened with one AI model then we can call another for response.
        // We are waiting only for 10 seconds until we gets the response otherwise we are calling another service.

        String aiResponse;

        Callable<String> generateAIResponseTask = () -> geminiService.generateDiet(prompt);

        Future<String> future = executorService.submit(generateAIResponseTask);

        try {
            // Waiting synchronously.
            aiResponse = future.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException ex) {
            log.info("Timeout exception, calling the perplexity service");
            // Calling another service.
            aiResponse = perplexityService.generateDiet(prompt);
        } catch (ExecutionException e) {
            log.error("Executor exception occurred, {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            log.error("The Thread was interrupted, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        // Filtering the JSON response if any invalid character founds.
        aiResponse = FilterResponse.filter(aiResponse);

        HealthResponse healthResponse;

        try {
            healthResponse = objectMapper.readValue(aiResponse, HealthResponse.class);
        } catch (Exception ex) {
            return ResponseEntity.internalServerError()
                    .body(new ResponseData());
        }

        assert healthResponse != null; // Throws assertion error if do null;

        // Since do we have to return the data.
        ResponseData newResponseData = ResponseData
                .builder()
                .patient(patientDTO)
                .healthResponse(healthResponse)
                .build();

        // We do have to generate the pdf since here so this could be the async-task.
        // Here we have to generate the file name.
        try {
            CompletableFuture<String> fileFuture = pdfService.generatePdfAsync(newResponseData, generateFileName(email));
            fileFuture
                    .thenAccept(fileAbsolutePath -> {
                        log.info("PDF generated successfully at path, {}", fileAbsolutePath);
                        supabaseService.saveReport(fileAbsolutePath);
                    })
                    .exceptionally(ex -> {
                        log.error(ex.getMessage());
                        return null;
                    });
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }


        // Here we have to cache the response.
        redisService.cacheDietPlan(email, newResponseData);

        return ResponseEntity.ok(newResponseData);
    }

    private String generateFileName(String email) {
        String fileprefix = email.split("@")[0];
        return fileprefix + "report";
    }

    private in.ayush.swasthyapath.dto.Patient mapToPatientDTO(Patient patient) {
        return in.ayush.swasthyapath.dto.Patient
                .builder()
                .name(patient.getName())
                .gender(patient.getGender())
                .age(patient.getAge())
                .height(patient.getHeight())
                .weight(patient.getWeight())
                .prakruti(patient.getPrakruti())
                .vikruti(patient.getVikruti())
                .assessmentDone(patient.getAssessmentDone())
                .dob(patient.getDob())
                .phoneNumber(patient.getPhoneNumber())
                .build();
    }

    private DoctorConsultedEvent mapToDoctorConsultedEvent(Patient patient) {
        return DoctorConsultedEvent
                .builder()
                .patientId(patient.getId())
                .patientName(patient.getName())
                .prakruti(patient.getPrakruti())
                .vikruti(patient.getVikruti())
                .agni(patient.getAgni())
                .guna(patient.getGuna())
                .rasa(patient.getRasa())
                .activityLevel(patient.getActivityLevel())
                .sleepingSchedule(patient.getSleepingSchedule())
                .mealFrequency(patient.getMealFrequency())
                .build();
    }

    public ResponseEntity<?> doAyurvedaAssessment(String email, AyurvedaAssessment assessment) {
        Patient patient = patientRepository.findPatientByEmail(email);

        if (patient == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthorized to access the resource"));
        }

        // Now when we find the user we need to some tasks.

        // Calculating the bmr of the user.
        final double bmr = calculateBMR(patient, assessment.getBasicAssessment());

        Dosha prakruti = doshaService.calculatePrakruti(assessment.getPrakrutiAssessment());
        Dosha vikruti = doshaService.calculateVikruti(assessment.getVikrutiAssessment());

        Map<String, Double> macroNutrient = getMacroNutrient(prakruti, bmr);

        // Now we have to do other tasks.
        Patient updatedPatient = updatePatientWithAssessmentInformation(patient, assessment, bmr, prakruti, vikruti, macroNutrient);

        // Also have to save the user data too.
        patientRepository.updatePatient(updatedPatient);

        // Now do we have to save the patient data.
        return ResponseEntity.ok(
                Map.of("response", "Ayurveda assessment completed successfully")
        );
    }

    /*
    * updatePatientWithAssessmentInformation: Simply a helper method for mapping the new patient data.*/
    private Patient updatePatientWithAssessmentInformation(
            Patient patient,
            AyurvedaAssessment assessment,
            double bmr,
            Dosha prakruti,
            Dosha vikruti,
            Map<String, Double> macroNutrient
            ) {
        patient.setBmr(bmr);
        patient.setMacroNutrient(macroNutrient);
        patient.setPrakruti(prakruti);
        patient.setVikruti(vikruti);
        patient.setAgni(assessment.getHealthAssessment().getAgni());
        patient.setActivityLevel(assessment.getBasicAssessment().getActivityLevel());
        patient.setAssessmentDone(true);
        patient.setWaterIntake(assessment.getBasicAssessment().getWaterIntake());
        patient.setPreferredFoodGenre(assessment.getBasicAssessment().getPreferredFoodGenre());
        patient.setMealFrequency(assessment.getBasicAssessment().getMealFrequency());
        return patient;
    }

    private double calculateBMR(Patient patient, BasicAssessment basicAssessment) {
        // Now we have to calculate the BMR.
        // BMR - Basal Metabolic Rate
        // Through this we basically calculate the number of calories we want for properly functioning the body.

        double bmr = 0;

        switch (patient.getGender()){

            case MALE ->
                    bmr = 66 + (13.7 * patient.getWeight())
                            + (5 * patient.getHeight())
                            - (6.8 * patient.getAge());

            case FEMALE ->
                    bmr = 655 + (9.6 * patient.getWeight())
                            + (1.8 * patient.getHeight())
                            - (4.7 * patient.getAge());

            case OTHER ->
                    bmr = 10 * patient.getWeight()
                            + 6.25 * patient.getHeight()
                            - 5 * patient.getAge();
        }

        double activityMultiplier = switch (basicAssessment.getActivityLevel()) {
            case SEDENTARY -> 1.2;
            case MODERATE -> 1.5;
            case ACTIVE -> 1.75;
        };

        return bmr * activityMultiplier;
    }

    private Map<String, Double> getMacroNutrient(Dosha prakriti, double bmr) {
        DoshaPercent doshaPercent = DOSHA_MAP.get(prakriti);
        return Map.of(
                "CARB", (doshaPercent.getCarbPercent() * bmr) / 4,
                "PROTEIN", (doshaPercent.getProteinPercent() * bmr) / 4,
                "FAT", (doshaPercent.getFatPercent() * bmr) / 9
        );
    }

    public ResponseEntity<?> downloadDietPlan(String email) {
        return supabaseService.downloadReport(generateFileName(email) + ".pdf");
    }

}
