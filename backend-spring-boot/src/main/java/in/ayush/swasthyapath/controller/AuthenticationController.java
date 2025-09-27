package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.dto.LoginDTO;
import in.ayush.swasthyapath.dto.Otp;
import in.ayush.swasthyapath.dto.Patient;
import in.ayush.swasthyapath.enums.UserType;
import in.ayush.swasthyapath.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup/patient")
    public ResponseEntity<String> handleSignUp(@RequestBody Patient patient) {
        authenticationService.handlePatientSignUp(patient);
        return ResponseEntity.ok("OTP has been sent successfully to the email.");
    }

    @PostMapping("/signup/patient/otp")
    public ResponseEntity<String> handleOtp(@RequestBody Otp otp) {
        return authenticationService.handlePatientOTP(otp);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, ?>> handleLogin(@RequestBody LoginDTO loginDTO) throws Exception {
        if (loginDTO.getUserType() == UserType.PATIENT) {
            return ResponseEntity
                    .ok(authenticationService.handlePatientLogin(loginDTO));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("response", "Haven't implemented other functionalities"));
        }
    }

}
