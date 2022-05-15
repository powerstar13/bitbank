package click.bitbank.api.domain.accountBook.model;

import click.bitbank.api.domain.accountBook.IncomeType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    @Column(value = "incomeDate")
    private LocalDateTime incomeDate; // 수입 날짜

    @Column(value = "incomeMoney")
    private BigInteger incomeMoney; // 수입 금액

    @Column(value = "incomeType")
    private IncomeType incomeType; // 수입 유형

    @Column(value = "memberId")
    private int memberId;

    @Column(value = "regDate")
    @CreatedDate
    private LocalDateTime regDate; // 생성일

    @Column(value = "modDate")
    @LastModifiedDate
    private LocalDateTime modDate; // 수정일

//    public Income(int incomeId, String incomeInfo, Timestamp , BigInteger incomeMoney, IncomeType incomeType, int memberId) {
//        this.incomeId = incomeId;
//        this.incomeInfo = incomeInfo;
//        this.expenditureDate = expenditureDate;
//        this.incomeMoney = incomeMoney;
//        this.incomeType = incomeType;
//        this.memberId = memberId;
//    }
}
