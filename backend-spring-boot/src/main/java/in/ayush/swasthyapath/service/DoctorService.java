package in.ayush.swasthyapath.service;

import in.ayush.swasthyapath.kafka.model.DoctorConsultedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter connect(String doctorId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(doctorId, emitter);

        emitter.onCompletion(() -> emitters.remove(doctorId));
        emitter.onTimeout(() -> emitters.remove(doctorId));
        emitter.onError(e -> emitters.remove(doctorId));

        return emitter;
    }

    public void sendConsultEvent(String doctorId, DoctorConsultedEvent doctorConsultedEvent) {
        SseEmitter emitter = emitters.get(doctorId);

        if (emitter != null) {
            try {
                emitter
                        .send(SseEmitter.event()
                                .name("consult")
                                .data(doctorConsultedEvent));
            } catch (IOException e) {
                log.error("Failed to send consult event to doctor, {}", e.getMessage());
                emitters.remove(doctorId);
            }
        }
    }
}
