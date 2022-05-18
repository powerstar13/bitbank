package click.bitbank.api.application.response.DTO;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DailyTotalDTO {

    private String day; // 일자
    
    private long total; // 일자 별 총 금액
}
