package click.bitbank.api.application.response.DTO;

import click.bitbank.api.domain.accountBook.model.AccountBookType;
import lombok.*;

import java.math.BigInteger;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookInfoDTO {

    AccountBookType accountBookType;    // 가계부 내역 유형

    String accountBookInfo;     // 가계부 내역 정보

    BigInteger accountMoney;  // 가계부 금액

}
