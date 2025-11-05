package in.ayush.swasthyapath.service;

import in.ayush.swasthyapath.enums.UserStatus;
import in.ayush.swasthyapath.kafka.model.DoctorConsultedEvent;
import in.ayush.swasthyapath.repository.DoctorRepository;
import in.ayush.swasthyapath.utils.JwtUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final JwtUtility jwtUtility;
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter connect(String token) throws Exception {

        SseEmitter emitter = null;
        String doctorEmail = jwtUtility.extractEmail(token);

        log.info("Doctor having email: {}, Subscribed to the SSE events", doctorEmail);

        if (doctorEmail == null) {
            log.error("Invalid token");
        } else {
            emitter = new SseEmitter(Long.MAX_VALUE);
            emitters.put(doctorEmail, emitter);
        }

        assert emitter != null;
        emitter.onCompletion(() -> emitters.remove(doctorEmail));
        emitter.onTimeout(() -> emitters.remove(doctorEmail));
        emitter.onError(e -> emitters.remove(doctorEmail));

        // Initial handshake.
        // This is Something similar to PING - PONG
        try {
            emitter.send(SseEmitter.event().
                    name("INIT")
                    .data("Connected established Successfully"));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }

        return emitter;
    }

    public void sendConsultEvent(String doctorEmail, DoctorConsultedEvent doctorConsultedEvent) {
        SseEmitter emitter = emitters.get(doctorEmail);

        if (emitter != null) {
            try {
                log.info("Sending the event to the email: {}", doctorEmail);
                emitter
                        .send(SseEmitter.event()
                                .name("consult")
                                .data(doctorConsultedEvent));
            } catch (IOException e) {
                log.error("Failed to send consult event to doctor, {}", e.getMessage());
                emitter.completeWithError(e);
                emitters.remove(doctorEmail);
            }
        } else {
            log.warn("Emitter is null failed to send the event to the doctor.");
        }
    }

    public List<DoctorConsultedEvent> getPendingPatients(String email) {
        return doctorRepository.findPendingPatients(email);
    }

    public ResponseEntity<?> logout(String email) {
        if (doctorRepository.updateDoctorStatus(email, UserStatus.OFFLINE) != null) {
            return ResponseEntity.ok(Map.of(
                    "response", "Logout successfully"
            ));
        }
        return ResponseEntity.badRequest().body(Map.of(
                "response", "Failed to logout, Try again later."
        ));
    }
}
