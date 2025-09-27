package in.ayush.swasthyapath.security;

import in.ayush.swasthyapath.model.Patient;
import in.ayush.swasthyapath.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsImpl implements UserDetailsService {

    private final PatientRepository patientRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Patient patient = patientRepository.findPatientByEmail(email);
        if (patient == null) throw new UsernameNotFoundException("Patient not found");
        return new CustomUserDetails(patient);
    }
}
