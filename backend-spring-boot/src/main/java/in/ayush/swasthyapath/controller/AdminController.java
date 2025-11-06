package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.dto.DoctorDTO;
import in.ayush.swasthyapath.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // Add Doctor Route.
    // This route is used for creating the doctor.
    @PostMapping("/create/doctor")
    public ResponseEntity<?> handleCreateDoctor(@RequestBody DoctorDTO doctorDTO) {
        return adminService.createDoctorAccount(doctorDTO);
    }

    @GetMapping("/fetch/data")
    public ResponseEntity<?> fetchData() {
        return adminService.fetchData();
    }


}
