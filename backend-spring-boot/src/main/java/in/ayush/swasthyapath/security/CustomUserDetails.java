package in.ayush.swasthyapath.security;

import in.ayush.swasthyapath.enums.UserType;
import in.ayush.swasthyapath.model.Admin;
import in.ayush.swasthyapath.model.Doctor;
import in.ayush.swasthyapath.model.Patient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final String id;
    private final String name;
    private final String email;
    private final String password;
    private final UserType userType;


    public CustomUserDetails(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.email = patient.getEmail();
        this.password = patient.getPassword();
        this.userType = UserType.PATIENT;
    }

    public CustomUserDetails(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.email = doctor.getEmail();
        this.password = doctor.getPassword();
        this.userType = UserType.DOCTOR;
    }

    public CustomUserDetails(Admin admin) {
        this.id = admin.getId();
        this.name = admin.getName();
        this.email = admin.getEmail();
        this.password = admin.getPassword();
        this.userType = UserType.ADMIN;
    }

    public String getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return email;
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
