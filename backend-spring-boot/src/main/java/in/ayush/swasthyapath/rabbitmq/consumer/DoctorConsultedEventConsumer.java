package in.ayush.swasthyapath.rabbitmq.consumer;

import in.ayush.swasthyapath.enums.DoctorConsultedStatus;
import in.ayush.swasthyapath.event.consumer.EventConsumer;
import in.ayush.swasthyapath.event.model.DoctorConsultedEvent;
import in.ayush.swasthyapath.model.Doctor;
import in.ayush.swasthyapath.repository.DoctorRepository;
import in.ayush.swasthyapath.repository.PatientRepository;
import in.ayush.swasthyapath.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("rabbit")
public class DoctorConsultedEventConsumer implements EventConsumer {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DoctorService doctorService;

    private List<Doctor> allDoctors;
    private List<Doctor> onlineDoctors;


    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queue-name}")
    public void consumeEvent(DoctorConsultedEvent consultedEvent) {

        log.info("RABBITMQ: Consuming the event: {}", consultedEvent.toString());

        onlineDoctors = doctorRepository.findAllOnlineDoctors();

        // If at-least one doctor is online we need to pass the data to that doctor.
        if (!onlineDoctors.isEmpty()) {
            int index = Math.abs(consultedEvent.getPatientId().hashCode()) % onlineDoctors.size();
            Doctor assignedDoctor = onlineDoctors.get(index);

            log.info("RABBITMQ: Assigned doctor: {} to Patient ID: {}", assignedDoctor.getName(), consultedEvent.getPatientId());

            // Here we just need to send the event to the doctor.
            log.info("RABBITMQ: Sending the consult event to the doctor having email: {}", assignedDoctor.getEmail());
            doctorService.sendConsultEvent(assignedDoctor.getEmail().trim(), consultedEvent);

            // Along with that we need to save the consulted event too to the doctor entity.
            doctorRepository.saveEvent(assignedDoctor.getEmail(), consultedEvent);
            log.info("RABBITMQ: Event has been successfully saved to the doctor with name: {}", assignedDoctor.getName());

            // When we do save the event we have to change the pending status to consulted but pending.
            patientRepository.updatePatientConsultedStatus(consultedEvent.getPatientId(), DoctorConsultedStatus.CONSULTED_BUT_PENDING);
            log.info("RABBITMQ: Successfully consulted to the doctor.");

        } else {
            log.info("RABBITMQ: No Doctor is online right now.");
            // Saving the event to any offline doctor.
            allDoctors = doctorRepository.findAllDoctors();
            if (allDoctors != null) {
                // Now we need to saving to one doctor.
                int index = Math.abs(consultedEvent.getPatientId().hashCode()) % allDoctors.size();
                Doctor offlineAssignedDoctor = allDoctors.get(index);

                log.info("RABBITMQ: Offline Assigned doctor: {} to Patient ID: {}", offlineAssignedDoctor.getName(), consultedEvent.getPatientId());

                // Now we need to save the event.
                doctorRepository.saveEvent(offlineAssignedDoctor.getEmail(), consultedEvent);
                log.info("RABBITMQ: Event has been successfully saved to the offline doctor with name: {}", offlineAssignedDoctor.getName());

                // When it saves the event we need to update the patient consulted status too.
                patientRepository.updatePatientConsultedStatus(consultedEvent.getPatientId(), DoctorConsultedStatus.CONSULTED_BUT_PENDING);
                log.info("RABBITMQ: Consulted to offline doctor.");
            }
        }
    }
}
