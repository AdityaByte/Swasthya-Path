package in.ayush.swasthyapath.repository;

// Using Criteria API because of its flexibility.

import in.ayush.swasthyapath.enums.DoctorConsultedStatus;
import in.ayush.swasthyapath.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

    private final MongoTemplate mongoTemplate;

    public Patient updatePatient(Patient patient) {
        Query query = new Query(Criteria.where("email").is(patient.getEmail()));
        return mongoTemplate.findAndReplace(query, patient);
    }

    public Patient findPatientByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        return mongoTemplate.findOne(query, Patient.class);
    }

    public Patient save(Patient patient) {
        return mongoTemplate.save(patient);
    }

    public boolean findPatientAssessmentReport(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        Patient patient = mongoTemplate.findOne(query, Patient.class);
        return patient.getAssessmentDone();
    }

    public Patient updatePatientConsultedStatus(String patientId, DoctorConsultedStatus status) {
        return mongoTemplate.findAndModify(
                new Query(Criteria.where("id").is(patientId)),
                new Update().set("doctorConsultedStatus", status),
                FindAndModifyOptions.options().returnNew(true),
                Patient.class);
    }

}
