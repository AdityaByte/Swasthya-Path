package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.kafka.model.DoctorConsultedEvent;
import in.ayush.swasthyapath.security.CustomUserDetails;
import in.ayush.swasthyapath.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("/consult")
    public SseEmitter handleConnect(@RequestParam("token") String token) throws Exception {
        return doctorService.connect(token);
    }

    @GetMapping("/pending/patients")
    public ResponseEntity<List<DoctorConsultedEvent>> handlePendingPatients(Principal principal) {
        List<DoctorConsultedEvent> result = doctorService.getPendingPatients(principal.getName());
        return ResponseEntity
                .ok(result == null ? Collections.emptyList() : result);
    }

}
