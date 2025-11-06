package in.ayush.swasthyapath.controller;

import in.ayush.swasthyapath.event.model.DoctorConsultedEvent;
import in.ayush.swasthyapath.service.DoctorService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping(
            value = "/consult",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter handleConnect(@RequestParam("token") String token, HttpServletResponse response) throws Exception {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Connection", "keep-alive");
        response.setHeader("X-Accel-Buffering", "no");

        return doctorService.connect(token);
    }

    @GetMapping("/pending/patients")
    public ResponseEntity<List<DoctorConsultedEvent>> handlePendingPatients(Principal principal) {
        List<DoctorConsultedEvent> result = doctorService.getPendingPatients(principal.getName());
        return ResponseEntity
                .ok(result == null ? Collections.emptyList() : result);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutHandler(Principal principal) {
        return doctorService.logout(principal.getName());
    }

}
