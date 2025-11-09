package in.ayush.swasthyapath.repository;

// Using Criteria API because of its flexibility.

import com.mongodb.client.result.UpdateResult;
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

    public Patient findPatientById(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), Patient.class);
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

    public boolean updatePatientConsultedStatus(String patientId, DoctorConsultedStatus status) {
        UpdateResult result =  mongoTemplate.updateFirst(
                new Query(Criteria.where("id").is(patientId)),
                new Update().set("doctorConsultedStatus", status),
                Patient.class);
        return result.getMatchedCount() > 0;
    }

    public long findHowManyPatients() {
        return mongoTemplate.count(new Query(), Patient.class);
    }

    public long findPendingConsultedPatients() {
        return mongoTemplate.count(
                new Query(new Criteria().orOperator(
                        Criteria.where("doctorConsultedStatus").is(DoctorConsultedStatus.PENDING),
                        Criteria.where("doctorConsultedStatus").is(DoctorConsultedStatus.CONSULTED_BUT_PENDING)
                )),
                Patient.class);
    }

    public boolean saveDoctorFeedback(String patientID, String feedback) {
        UpdateResult result = mongoTemplate
                .updateFirst(
                        new Query(Criteria.where("id").is(patientID)),
                        new Update().set("doctorFeedback", feedback),
                        Patient.class
                );
        return result.getModifiedCount() > 0;
    }

}
