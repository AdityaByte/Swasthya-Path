package in.ayush.swasthyapath.event.model;

import lombok.*;
import org.springframework.context.annotation.Profile;

@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Profile("kafka")
public class DoctorConsultedEvent {
    private String patientId;
    private String patientName;
}