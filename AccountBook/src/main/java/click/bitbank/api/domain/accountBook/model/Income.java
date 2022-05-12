package click.bitbank.api.domain.accountBook.model;

import click.bitbank.api.domain.accountBook.IncomeType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "income")
public class Income {

    @Id
    @Column(value = "incomeId")
    private int incomeId; // 수입 번호

    @Column(value = "incomeInfo")
    private String incomeInfo; // 수입 내역

    @Column(value = "expenditureDate")
    private Timestamp expenditureDate; // 지출 날짜

    @Column(value = "incomeMoney")
    private BigInteger incomeMoney; // 수입 금액

    @Column(value = "incomeType")
    private IncomeType incomeType; // 수입 유형

    @Column(value = "regDate")
    private Timestamp regDate; // 생성일

    @Column(value = "modDate")
    private Timestamp modDate; // 수정일
}
