package in.ayush.swasthyapath.dto;

import in.ayush.swasthyapath.enums.BodyType;
import in.ayush.swasthyapath.enums.DigestionStrength;
import in.ayush.swasthyapath.enums.SkinType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrakrutiAssessment {

    private BodyType bodyType;
    private SkinType skinNature;
    private DigestionStrength digestionStrength;
    private String energyPattern;
    private String sleepNature;

}
