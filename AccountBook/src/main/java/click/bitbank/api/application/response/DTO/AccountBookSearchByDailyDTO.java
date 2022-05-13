package click.bitbank.api.application.response.DTO;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookSearchByDailyDTO {

    String date;    // 날짜

    String day;     // 요일

    long accountBookTotalByDaily;   // 해당 날짜 가계부 총 금액

    List<AccountBookInfoDTO> accountBookInfoDTOList;


}
