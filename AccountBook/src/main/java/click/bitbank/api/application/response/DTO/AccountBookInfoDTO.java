package click.bitbank.api.application.response.DTO;

import click.bitbank.api.domain.model.accountBook.AccountBookType;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookInfoDTO {

    AccountBookType accountBookType;    // 가계부 내역 유형

    String accountBookInfo;     // 가계부 내역 정보

    long accountMoney;  // 가계부 금액

}
