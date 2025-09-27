package in.ayush.swasthyapath.service;

import in.ayush.swasthyapath.dto.LoginDTO;
import in.ayush.swasthyapath.dto.Otp;
import in.ayush.swasthyapath.dto.Patient;
import in.ayush.swasthyapath.exception.UserAlreadyExists;
import in.ayush.swasthyapath.repository.PatientRepository;
import in.ayush.swasthyapath.security.CustomUserDetails;
import in.ayush.swasthyapath.utils.JwtUtility;
import in.ayush.swasthyapath.utils.OtpUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;

    Map<String, Patient> tempStorage = new HashMap<>();

    public void handlePatientSignUp(Patient patient) {
        // Do have to check that the patient is previously existed or not.
        // A person do have only one phone-number linked with each other or email do ok.
        // Right now we are just checking for the email.
        in.ayush.swasthyapath.model.Patient fetchedPatient = patientRepository.findPatientByEmail(patient.getEmail());

        if (fetchedPatient != null) {
            throw new UserAlreadyExists("User with email: " + patient.getEmail() + " is already exists.");
        }

        // If the user is null we do have to create an OTP of 6 digits and send it to the user's email.
        String otp = OtpUtility.generateOTP(patient.getEmail());
        log.info("OTP is: {}", otp);

        // Sending OTP logic.

        // When the otp has been sent now i need to store the user to the temporary storage.
        tempStorage.put(patient.getEmail(), patient);
    }

    public ResponseEntity<String> handlePatientOTP(Otp otp) {
        // Now we need to check that the user by the email exists or not.
        Patient storedPatient = tempStorage.get(otp.getEmail());

        if (storedPatient == null) {
            return ResponseEntity
                    .badRequest()
                    .body("No user found of the email");
        }

        // Now we have to check that the user's entered OTP is valid or not.
        if (!OtpUtility.isValid(otp)) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid OTP");
        }

        // If the OTP is valid now I just need to save the patient data to database.
        in.ayush.swasthyapath.model.Patient finalData = mapToModelPatient(storedPatient);
        finalData.setPassword(passwordEncoder.encode(storedPatient.getPassword()));

        // Now we have to save the Patient.
        in.ayush.swasthyapath.model.Patient savedPatient = patientRepository.save(finalData);

        return new ResponseEntity<>("Patient Signup successfully.", HttpStatus.CREATED);
    }

    private in.ayush.swasthyapath.model.Patient mapToModelPatient(Patient patient) {
        return in.ayush.swasthyapath.model.Patient
                .builder()
                .name(patient.getName())
                .email(patient.getEmail())
                .height(patient.getHeight())
                .weight(patient.getWeight())
                .gender(patient.getGender())
                .build();
    }

    // Login Handler Service methods.
    public Map<String, ?> handlePatientLogin(LoginDTO loginDTO) throws Exception {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String token = jwtUtility.generateToken(customUserDetails.getUsername(), customUserDetails.getEmail());
        Date expiry = jwtUtility.getExpirationDate(token);

        boolean assessmentReport = patientRepository.findPatientAssessmentReport(loginDTO.getEmail());

        return Map.of(
                "assessment", assessmentReport,
                "token", token,
                "expiry", expiry
        );
    }

}
