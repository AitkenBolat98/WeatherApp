package DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForecastDTO {

    private String name;
    private Double temperature;
    private Double feels_like;
    private Integer windSpeed;
    private Integer windDegree;
}
