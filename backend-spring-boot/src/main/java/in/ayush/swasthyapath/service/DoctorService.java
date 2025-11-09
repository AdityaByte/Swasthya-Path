package in.ayush.swasthyapath.service;

import in.ayush.swasthyapath.dto.DoctorFeedbackRequestDTO;
import in.ayush.swasthyapath.dto.PatientConsultedEventDTO;
import in.ayush.swasthyapath.enums.DoctorConsultedStatus;
import in.ayush.swasthyapath.enums.UserStatus;
import in.ayush.swasthyapath.event.model.DoctorConsultedEvent;
import in.ayush.swasthyapath.model.Patient;
import in.ayush.swasthyapath.repository.DoctorRepository;
import in.ayush.swasthyapath.repository.PatientRepository;
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

    private final PatientRepository patientRepository;
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

    public ResponseEntity<?> fetchPatientConsultation(String patientID) {
        Patient patient = patientRepository.findPatientById(patientID.trim());
        if (patient == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of(
                            "response", "No patient found of the Patient ID"
                    ));
        }
        // Else we need to map the data to the PatientConsultedEventDTO
        return ResponseEntity
                .ok(mapToPatientConsultedEventDTO(patient));
    }

    private PatientConsultedEventDTO mapToPatientConsultedEventDTO(Patient patient) {
        return PatientConsultedEventDTO
                .builder()
                .id(patient.getId())
                .name(patient.getName())
                .prakruti(patient.getPrakruti())
                .vikruti(patient.getVikruti())
                .age(patient.getAge())
                .height(patient.getHeight())
                .weight(patient.getWeight())
                .gender(patient.getGender())
                .assessment(patient.getAssessment())
                .build();
    }

    public ResponseEntity<?> approvePatient(String doctorEmail, String patientID) {
        if (!patientRepository.updatePatientConsultedStatus(patientID, DoctorConsultedStatus.APPROVED)) {
           return ResponseEntity
                   .internalServerError()
                   .body(Map.of(
                           "response", "Failed to set the consulted status of patient with ID: " + patientID + " to approved"
                   ));
        }

        if (!doctorRepository.removePendingConsultedPatient(doctorEmail, patientID)) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of(
                            "response", "Failed to remove the pending consulted patient from doctor entity"
                    ));
        }

        return ResponseEntity
                .ok(Map.of(
                        "response", "Patient's Data has been approved by doctor"
                ));
    }

    public ResponseEntity<?> doDoctorFeedback(String doctorEmail, DoctorFeedbackRequestDTO feedbackDTO) {
        // Here we need to save the current feedback of doctor to the patient database.
        if (!patientRepository.saveDoctorFeedback(feedbackDTO.getPatientID(), feedbackDTO.getFeedback())) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of(
                            "response", "Failed to save the feedback to the db for the patient with id: " + feedbackDTO.getPatientID()
                    ));
        }

        // When its do we also need to remove the data from the doctor's email.
        if (!doctorRepository.removePendingConsultedPatient(doctorEmail, feedbackDTO.getPatientID())) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of(
                            "response", "Failed to remove the pending consulted patient from doctor entity"
                    ));
        }

        // If everything goes correctly then we just return the final response.

        // Else we need to return saved.
        return ResponseEntity
                .ok(Map.of(
                        "response", "Consultation has been saved successfully to the patient database"
                ));
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
