package in.ayush.swasthyapath.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("admin_data")
public class Admin {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
}
