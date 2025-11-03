package in.ayush.swasthyapath.security;

import in.ayush.swasthyapath.enums.UserStatus;
import in.ayush.swasthyapath.enums.UserType;
import in.ayush.swasthyapath.model.Admin;
import in.ayush.swasthyapath.model.Doctor;
import in.ayush.swasthyapath.model.Patient;
import in.ayush.swasthyapath.repository.AdminRepository;
import in.ayush.swasthyapath.repository.DoctorRepository;
import in.ayush.swasthyapath.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsImpl implements UserDetailsService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AdminRepository adminRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String emailAndRole) throws UsernameNotFoundException {

        String[] arr = emailAndRole.split(":");
        String email = arr[0];
        UserType userType = UserType.valueOf(arr[1]);

        switch (userType) {
            case PATIENT -> {
                Patient patient = patientRepository.findPatientByEmail(email);
                if (patient == null) throw new UsernameNotFoundException("No patient exists of the email");
                return new CustomUserDetails(patient);
            }
            case DOCTOR -> {
                Doctor doctor = doctorRepository.findDoctorByEmail(email);
                if (doctor == null) throw new UsernameNotFoundException("No doctor exists of the email");
                // If the user exists we have to update the user to online.
                doctorRepository.updateDoctorStatus(doctor.getEmail(), UserStatus.ONLINE);
                return new CustomUserDetails(doctor);
            }
            case ADMIN -> {
                Admin admin = adminRepository.findAdmin(email);
                if (admin == null) throw new UsernameNotFoundException("No Admin exists of the email");
                return new CustomUserDetails(admin);
            }
            default -> {
                log.error("No UserType found: {}", String.valueOf(userType));
                return null;
            }
        }
    }
}
