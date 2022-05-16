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

    private LocalDateTime date;

    private String info;

    private BigInteger money;

    private AccountBookType accountBookType;    // 가계부 타입 (수입, 지출, 이체)
}
