package in.ayush.swasthyapath.service;

import in.ayush.swasthyapath.dto.LoginDTO;
import in.ayush.swasthyapath.dto.Otp;
import in.ayush.swasthyapath.dto.Patient;
import in.ayush.swasthyapath.enums.UserType;
import in.ayush.swasthyapath.exception.UserAlreadyExists;
import in.ayush.swasthyapath.repository.PatientRepository;
import in.ayush.swasthyapath.security.CustomUserDetails;
import in.ayush.swasthyapath.service.mail.MailService;
import in.ayush.swasthyapath.utils.GeneralUtility;
import in.ayush.swasthyapath.utils.JwtUtility;
import in.ayush.swasthyapath.utils.MailUtility;
import in.ayush.swasthyapath.utils.OtpUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtility;
    private final MailService mailService;
    private final MailUtility mailUtility;

    // Making it concurrent so that no race condition will occur and multiple threads
    // can access it.
    Map<String, Patient> tempStorage = new ConcurrentHashMap<>();

    public ResponseEntity<?> handlePatientSignUp(Patient patient) {
        // Do have to check that the patient is previously existed or not.
        // A person do have only one phone-number linked with each other or email do ok.
        // Right now we are just checking for the email.
        in.ayush.swasthyapath.model.Patient fetchedPatient = patientRepository.findPatientByEmail(patient.getEmail());

        if (fetchedPatient != null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "respose", "User Already exists, of the email, Please Login"
                    ));
        }

        // If the user is null we do have to create an OTP of 6 digits and send it to the user's email.
        String otp = OtpUtility.generateOTP(patient.getEmail());

        // Commenting the below line for production.
        // log.info("OTP is: {}", otp);

        // Sending OTP logic.
        String mailBody = mailUtility.createOTPMessage(patient.getName(), otp);

        // Now sending the mail.
        if (!mailService.sendMail(patient.getEmail(), "Your Swasthya Path OTP code", mailBody)) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "response", "Failed to send email, Try again later"
                    ));
        }

        log.info("OTP Email has been successfully sent to the person email: {}", patient.getEmail());

        // When the otp has been sent now I need to store the user to the temporary storage.
        tempStorage.put(patient.getEmail(), patient);

        return ResponseEntity
                .ok(Map.of(
                        "response", "OTP has been successfully sent to the person's email"
                ));
    }

    public ResponseEntity<?> handlePatientOTP(Otp otp) {
        // Now we need to check that the user by the email exists or not.
        Patient storedPatient = tempStorage.get(otp.getEmail());

        if (storedPatient == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "response", "No user found of the email"
                    ));
        }

        // Now we have to check that the user's entered OTP is valid or not.
        if (!OtpUtility.isValid(otp)) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "response", "Invalid OTP"
                    ));
        }

        // If the OTP is valid now I just need to save the patient data to database.
        in.ayush.swasthyapath.model.Patient finalData = mapToModelPatient(storedPatient);
        finalData.setPassword(passwordEncoder.encode(storedPatient.getPassword()));

        // Now we have to save the Patient.
        in.ayush.swasthyapath.model.Patient savedPatient = patientRepository.save(finalData);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of(
                        "response", "User created successfully"
                ));
    }

    private in.ayush.swasthyapath.model.Patient mapToModelPatient(Patient patient) {
        return in.ayush.swasthyapath.model.Patient
                .builder()
                .name(patient.getName())
                .email(patient.getEmail())
                .age(GeneralUtility.calculateAge(patient.getDob()))
                .height(patient.getHeight())
                .weight(patient.getWeight())
                .gender(patient.getGender())
                .dob(patient.getDob())
                .phoneNumber(patient.getPhoneNumber())
                .build();
    }

    // Login Handler Service methods.
    public ResponseEntity<?> handleLogin(LoginDTO loginDTO) {
        try {
            Map<String, Object> responseMap = new HashMap<>();

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            concatEmailAndRole(loginDTO.getEmail(), loginDTO.getUserType()),
                            loginDTO.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            String token = jwtUtility.generateToken(customUserDetails.getId(), customUserDetails.getName(), customUserDetails.getUsername(), customUserDetails.getUserType());
            Date expiry = jwtUtility.getExpirationDate(token);

            if (loginDTO.getUserType().equals(UserType.PATIENT)) {
                boolean assessmentReport = patientRepository.findPatientAssessmentReport(loginDTO.getEmail());
                responseMap.put("assessment", assessmentReport);
            }

            responseMap.put("token", token);
            responseMap.put("expiry", expiry);

            return ResponseEntity
                    .ok(responseMap);
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("response", "Invalid credentials"));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("response", "User Not found!"));
        } catch (Exception e) {
            log.error("Something went wrong, {}", e.getMessage());
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of(
                            "response", "Something went wrong, Try again later"
                    ));
        }
    }

    private String concatEmailAndRole(String email, UserType userType) {
        return String.format("%s:%s", email.trim(), userType.name().trim());
    }

}
