package click.bitbank.api.domain.accountBook.model.income;

import click.bitbank.api.domain.accountBook.AccountBookType;
import click.bitbank.api.domain.accountBook.model.Classification;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "income")
public class Income extends Classification {

    @Id
    @Column(value = "incomeId")
    private int incomeId; // 수입 번호

    @Column(value = "incomeInfo")
    private String incomeInfo; // 수입 내역

    @Column(value = "incomeDate")
    private LocalDateTime incomeDate; // 수입 날짜

    @Column(value = "incomeMoney")
    private BigInteger incomeMoney; // 수입 금액

    @Column(value = "incomeType")
    private IncomeType incomeType; // 수입 유형

    @Column(value = "memberId")
    private int memberId;

    public AccountBookType getAccountBookType() {
        return AccountBookType.I;
    }
}
