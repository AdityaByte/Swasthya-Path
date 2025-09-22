package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.dto.HealthResponse;
import in.ayush.swasthyapath.dto.PatientInformation;
import in.ayush.swasthyapath.dto.ResponseData;
import in.ayush.swasthyapath.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "http://localhost:5173")
public class MainController {

    private final PatientService patientService;

    @PostMapping("/survey")
    public ResponseEntity<HealthResponse> getPatientInformation(@RequestBody PatientInformation patientInformation) {
        HealthResponse healthResponse = patientService.prepareDiet(patientInformation);
        if (healthResponse == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(healthResponse);
    }

    @PostMapping("/get")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseData getAllInfoAboutPatient(@RequestBody PatientInformation patientInformation) {
        return patientService.getInformation(patientInformation);
    }
}
