package in.ayush.swasthyapath.service;

import in.ayush.swasthyapath.dto.DoctorDTO;
import in.ayush.swasthyapath.model.Doctor;
import in.ayush.swasthyapath.repository.DoctorRepository;
import in.ayush.swasthyapath.utils.GeneralUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> createDoctorAccount(DoctorDTO doctorDTO) {

        // Before we need to check that any doctor previously exists by the AYUSH id or email.
        if (doctorRepository.checkDoctorExistsByEmailOrRegistrationNumber(doctorDTO.getEmail(), doctorDTO.getRegistrationNumber()) != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Doctor already exists of the preferred email or registration number.");
        }

        // Here we need to create the Doctor.
        Doctor doctor = mapToDoctorModel(doctorDTO);
        return doctorRepository
                .createDoctor(doctor)
                .map(doc ->  ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(String.format("Doctor {%s} has been created successfully.", doc.getName())))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Doctor could not be created"));
    }

    private Doctor mapToDoctorModel(DoctorDTO doctorDTO) {
        return Doctor
                .builder()
                .name(doctorDTO.getName())
                .dob(doctorDTO.getDob())
                .gender(doctorDTO.getGender())
                .email(doctorDTO.getEmail())
                .password(passwordEncoder.encode(doctorDTO.getPassword()))
                .qualification(doctorDTO.getQualification())
                .experienceYears(doctorDTO.getExperienceYears())
                .doshaExpertise(doctorDTO.getDoshaExpertise())
                .age(GeneralUtility.calculateAge(doctorDTO.getDob()))
                .specialization(doctorDTO.getSpecialization())
                .build();
    }

}
