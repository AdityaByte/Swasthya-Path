package in.ayush.swasthyapath.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthResponse {
    private DayPlan dayPlan;
    private List<String> guidelines;
    private String pdfURL;
}