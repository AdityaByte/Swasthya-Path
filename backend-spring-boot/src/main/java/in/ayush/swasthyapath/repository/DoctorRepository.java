package in.ayush.swasthyapath.repository;

import in.ayush.swasthyapath.model.Doctor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class DoctorRepository {

    private final MongoTemplate mongoTemplate;

    public Doctor findDoctorByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return mongoTemplate.findOne(query, Doctor.class);
    }

}
