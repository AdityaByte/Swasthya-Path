package in.ayush.swasthyapath.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoshaPercent {
    private double carbPercent;
    private double proteinPercent;
    private double fatPercent;
}
