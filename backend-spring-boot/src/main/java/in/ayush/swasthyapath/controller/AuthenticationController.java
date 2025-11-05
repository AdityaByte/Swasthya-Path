package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.dto.LoginDTO;
import in.ayush.swasthyapath.dto.Otp;
import in.ayush.swasthyapath.dto.Patient;
import in.ayush.swasthyapath.enums.UserType;
import in.ayush.swasthyapath.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
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
        return authenticationService.handlePatientSignUp(patient);
    }

    @PostMapping("/signup/patient/otp")
    public ResponseEntity<String> handleOtp(@RequestBody Otp otp) {
        return authenticationService.handlePatientOTP(otp);
    }

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody LoginDTO loginDTO) throws Exception {
        Map<String, ?> result = authenticationService.handleLogin(loginDTO);
        if (result.get("token") == null) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Invalid credentials");
        }
        return ResponseEntity.ok(result);
    }

}
