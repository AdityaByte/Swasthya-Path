package in.ayush.swasthyapath.repository;

import in.ayush.swasthyapath.enums.UserStatus;
import in.ayush.swasthyapath.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class DoctorRepository {

    private final MongoTemplate mongoTemplate;

    public Doctor findDoctorByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, Doctor.class);
    }

    public Doctor checkDoctorExistsByEmailOrRegistrationNumber(String email, String regNo) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("email").is(email),
                Criteria.where("registrationNumber").is(regNo)
        ));

        return mongoTemplate.findOne(query, Doctor.class);
    }

    public Optional<Doctor> createDoctor(Doctor doctor) {
        return Optional.of(mongoTemplate.insert(doctor));
    }

    public Doctor updateDoctorStatus(String email, UserStatus userStatus) {
        return mongoTemplate.findAndModify(
                new Query(Criteria.where("email").is(email)),
                new Update().set("status", UserStatus.ONLINE),
                FindAndModifyOptions.options().returnNew(true),
                Doctor.class);
    }

    public List<Doctor> findAllOnlineDoctors() {
        return mongoTemplate.find(new Query(Criteria.where("status").is(UserStatus.ONLINE)), Doctor.class);
    }

}
