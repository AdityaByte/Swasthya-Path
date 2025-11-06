package in.ayush.swasthyapath.kafka.consumer;

import in.ayush.swasthyapath.enums.DoctorConsultedStatus;
import in.ayush.swasthyapath.event.consumer.EventConsumer;
import in.ayush.swasthyapath.event.model.DoctorConsultedEvent;
import in.ayush.swasthyapath.model.Doctor;
import in.ayush.swasthyapath.repository.DoctorRepository;
import in.ayush.swasthyapath.repository.PatientRepository;
import in.ayush.swasthyapath.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("kafka")
public class DoctorConsultedEventConsumer implements EventConsumer {

    private List<Doctor> allDoctors;
    private List<Doctor> onlineDoctors;
    private final DoctorRepository doctorRepository;
    private final DoctorService doctorService;
    private final PatientRepository patientRepository;

    @Override
    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEvent(DoctorConsultedEvent doctorConsultedEvent) {
        // Here we consumed the event.
        // Now we just need to do something like
        log.info("Consuming the event: {}", doctorConsultedEvent.toString());

        // Here we need to update the db.
        // Here we need to pass the event to the doctor and also update the doctor consulted data.
        onlineDoctors = doctorRepository.findAllOnlineDoctors();

        // If atleast one doctor is online we need to pass the data to that doctor.
        if (!onlineDoctors.isEmpty()) {
            int index = Math.abs(doctorConsultedEvent.getPatientId().hashCode()) % onlineDoctors.size();
            Doctor assignedDoctor = onlineDoctors.get(index);

            log.info("Assigned doctor: {} to Patient ID: {}", assignedDoctor.getName(), doctorConsultedEvent.getPatientId());

            // Here we just need to send the event to the doctor.
            log.info("Sending the consult event to the doctor having email: {}", assignedDoctor.getEmail());
            doctorService.sendConsultEvent(assignedDoctor.getEmail().trim(), doctorConsultedEvent);

            // Along with that we need to save the consulted event too to the doctor entity.
            doctorRepository.saveEvent(assignedDoctor.getEmail(), doctorConsultedEvent);
            log.info("Event has been successfully saved to the doctor with name: {}", assignedDoctor.getName());

            // When we do save the event we have to change the pending status to consulted but pending.
            patientRepository.updatePatientConsultedStatus(doctorConsultedEvent.getPatientId(), DoctorConsultedStatus.CONSULTED_BUT_PENDING);
            log.info("Successfully consulted to the doctor.");

        } else {
            log.info("No Doctor is online right now.");
            // Saving the event to any offline doctor.
            allDoctors = doctorRepository.findAllDoctors();
            if (allDoctors != null) {
                // Now we need to saving to one doctor.
                int index = Math.abs(doctorConsultedEvent.getPatientId().hashCode()) % allDoctors.size();
                Doctor offlineAssignedDoctor = allDoctors.get(index);

                log.info("Offline Assigned doctor: {} to Patient ID: {}", offlineAssignedDoctor.getName(), doctorConsultedEvent.getPatientId());

                // Now we need to save the event.
                doctorRepository.saveEvent(offlineAssignedDoctor.getEmail(), doctorConsultedEvent);
                log.info("Event has been successfully saved to the offline doctor with name: {}", offlineAssignedDoctor.getName());

                // When it saves the event we need to update the patient consulted status too.
                patientRepository.updatePatientConsultedStatus(doctorConsultedEvent.getPatientId(), DoctorConsultedStatus.CONSULTED_BUT_PENDING);
                log.info("Consulted to offline doctor.");
            }
        }
    }

}
