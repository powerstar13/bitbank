package click.bitbank.api.application.response;

import click.bitbank.api.application.response.DTO.AccountBookSearchByDailyDTO;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookSearchResponse extends SuccessResponse {

    List<AccountBookSearchByDailyDTO> accountBookSearchByDailyDTOList;

    BigInteger incomeTotal;  // 수입 총 금액

    BigInteger expenditureTotal;  // 지출 총 금액
}
