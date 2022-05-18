package click.bitbank.api.application.response;

import click.bitbank.api.application.response.DTO.DonutGraphDTO;
import click.bitbank.api.application.response.DTO.WeeklyTotalDTO;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.*;

import java.util.List;


@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookStatisticResponse extends SuccessResponse {

    private Long monthlyTotal; // 해당 월 총 금액
    
    private List<WeeklyTotalDTO> weeklyTotalDTOList; // 주 별 총 금액 목록
    
    private List<DonutGraphDTO> donutGraphDTOList; // 카테고리 별 비중 및 금액 목록

    private List<Long> lineGraphDailyTotalList; // 일 별 총 금액 목록
    private List<String> lineGraphDayList; // 일자 목록
}
