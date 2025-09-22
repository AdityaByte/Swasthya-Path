package in.ayush.swasthyapath.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayPlan {
    private String breakfast;
    private String lunch;
    private String dinner;
    private String snacks;
}