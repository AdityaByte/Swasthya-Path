package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.dto.PatientInformation;
import in.ayush.swasthyapath.dto.ResponseData;
import in.ayush.swasthyapath.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MainController {

    private final PatientService patientService;

    @PostMapping("/patient")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public String getPatientInformation(@RequestBody PatientInformation patientInformation) {
        return patientService.prepareDiet(patientInformation);
    }

    @PostMapping("/get")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseData getAllInfoAboutPatient(@RequestBody PatientInformation patientInformation) {
        return patientService.getInformation(patientInformation);
    }
}
