package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.dto.LoginDTO;
import in.ayush.swasthyapath.dto.Otp;
import in.ayush.swasthyapath.dto.Patient;
import in.ayush.swasthyapath.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup/patient")
    public ResponseEntity<?> handleSignUp(@RequestBody Patient patient) {
        return authenticationService.handlePatientSignUp(patient);
    }

    @PostMapping("/signup/patient/otp")
    public ResponseEntity<?> handleOtp(@RequestBody Otp otp) {
        return authenticationService.handlePatientOTP(otp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody LoginDTO loginDTO) throws Exception {
        return authenticationService.handleLogin(loginDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> handleTokenRefreshment(@RequestBody Map<String, String> request) {
        return authenticationService.refreshToken(request.get("refreshToken"));
    }

}
