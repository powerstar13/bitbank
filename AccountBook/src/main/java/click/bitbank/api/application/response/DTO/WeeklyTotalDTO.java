package click.bitbank.api.application.response.DTO;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyTotalDTO {

    private String week; // 해당 월 주차 --> 0월 0주
    
    private long total; // 주차 별 총 금액
}
