package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.dto.DoctorDTO;
import in.ayush.swasthyapath.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Add Doctor Route.
    // This route is used for creating the doctor.
    @PostMapping("/create/doctor")
    public ResponseEntity<String> handleCreateDoctor(@RequestBody DoctorDTO doctorDTO) {
        return adminService.createDoctorAccount(doctorDTO);
    }


}
