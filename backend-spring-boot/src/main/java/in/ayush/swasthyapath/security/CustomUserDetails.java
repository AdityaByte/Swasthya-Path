package in.ayush.swasthyapath.security;

import in.ayush.swasthyapath.enums.UserType;
import in.ayush.swasthyapath.model.Doctor;
import in.ayush.swasthyapath.model.Patient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String name;
    private final String email;
    private final String password;
    private final UserType userType;


    public CustomUserDetails(Patient patient) {
        this.name = patient.getName();
        this.email = patient.getEmail();
        this.password = patient.getPassword();
        this.userType = UserType.PATIENT;
    }

    public CustomUserDetails(Doctor doctor) {
        this.name = doctor.getName();
        this.email = doctor.getEmail();
        this.password = doctor.getPassword();
        this.userType = UserType.DOCTOR;
    }

    public String getEmail() {
        return this.email;
    }

    public UserType getUserType() {
        return this.userType;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.userType.name())); // No roles
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
