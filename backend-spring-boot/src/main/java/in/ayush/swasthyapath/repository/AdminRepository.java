package in.ayush.swasthyapath.repository;

import in.ayush.swasthyapath.model.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

    private final MongoTemplate mongoTemplate;

    public Admin findAdmin(String email) {
        return mongoTemplate.findOne(new Query(Criteria.where("email").is(email)), Admin.class);
    }


}
