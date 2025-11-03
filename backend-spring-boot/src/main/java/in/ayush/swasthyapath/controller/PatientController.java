package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.dto.AyurvedaAssessment;
import in.ayush.swasthyapath.dto.ResponseData;
import in.ayush.swasthyapath.security.CustomUserDetails;
import in.ayush.swasthyapath.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/assessment")
    public ResponseEntity<?> handleAyurvedaAssessmentPatient(Principal principal, @RequestBody AyurvedaAssessment ayurvedaAssessment) {
        return patientService.doAyurvedaAssessment(principal.getName(), ayurvedaAssessment);
    }

    @PostMapping("/diet")
    public ResponseEntity<ResponseData> handleGetDietRequest(Principal principal) {
        return patientService.planDiet(principal.getName());
    }

    @GetMapping("/report")
    public ResponseEntity<?> downloadReport(Principal principal) {
        return patientService.downloadDietPlan(principal.getName());
    }

    @GetMapping("/test")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("This route is working");
    }

}
