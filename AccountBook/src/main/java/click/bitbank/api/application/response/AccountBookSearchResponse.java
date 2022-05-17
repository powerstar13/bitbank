package click.bitbank.api.application.response;

import click.bitbank.api.application.response.DTO.AccountBookSearchByDailyDTO;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookSearchResponse {

    List<AccountBookSearchByDailyDTO> accountBookSearchByDailyDTOList;
}
