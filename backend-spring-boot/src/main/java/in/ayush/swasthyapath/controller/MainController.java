package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.dto.HealthResponse;
import in.ayush.swasthyapath.dto.AyurvedaAssessment;
import in.ayush.swasthyapath.dto.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class MainController {

    public void handleAyurvedaAssessmentPatient(@RequestBody AyurvedaAssessment ayurvedaAssessment) {
        return;
    }

}
