package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.dto.AyurvedaAssessment;
import in.ayush.swasthyapath.dto.ResponseData;
import in.ayush.swasthyapath.service.PatientAssessmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final PatientAssessmentService patientAssessmentService;

    @PostMapping("/assessment")
    public ResponseEntity<?> handleAyurvedaAssessmentPatient(Principal principal, @RequestBody AyurvedaAssessment ayurvedaAssessment) {
        return patientAssessmentService.doAyurvedaAssessment(principal.getName(), ayurvedaAssessment);
    }

    @PostMapping("/diet")
    public ResponseEntity<ResponseData> handleGetDietRequest(Principal principal) {
        return patientAssessmentService.planDiet(principal.getName());
    }

    @GetMapping("/report")
    public ResponseEntity<?> downloadReport(Principal principal) {
        return patientAssessmentService.downloadDietPlan(principal.getName());
    }

}
