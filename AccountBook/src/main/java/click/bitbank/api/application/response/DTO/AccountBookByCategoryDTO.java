package click.bitbank.api.application.response.DTO;

import click.bitbank.api.domain.accountBook.AccountBookType;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookByCategoryDTO {

    private LocalDateTime date; // 가계부 날짜

    private String info;    // 가계부 정보

    private BigInteger money;   // 가계부 금액

    private AccountBookType accountBookType;    // 가계부 타입 (수입, 지출, 이체)
}
