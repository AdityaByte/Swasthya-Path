package in.ayush.swasthyapath.dto;

import in.ayush.swasthyapath.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    private UserType userType;
    private String email;
    private String password;

}
