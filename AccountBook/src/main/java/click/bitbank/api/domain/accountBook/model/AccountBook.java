package click.bitbank.api.domain.accountBook.model;

import click.bitbank.api.domain.accountBook.model.expenditure.ExpenditureType;
import click.bitbank.api.domain.accountBook.model.income.IncomeType;
import click.bitbank.api.domain.accountBook.model.transfer.TransferType;
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
@Table(value = "accountBook")
public class AccountBook extends Classification {

    @Id
    @Column(value = "accountBookId")
    private int accountBookId; // 가계부 번호

    @Column(value = "accountBookInfo")
    private String accountBookInfo; // 가계부 내역

    @Column(value = "accountBookDate")
    private LocalDateTime accountBookDate; // 가계부 작성 날짜

    @Column(value = "accountBookMoney")
    private BigInteger accountBookMoney; // 금액

    @Column(value = "accountBookType")
    private IncomeType incomeType; // 수입 유형

    @Column(value = "expenditureType")
    private ExpenditureType expenditureType; // 지출 유형

    @Column(value = "transferType")
    private TransferType transferType; // 이체 내역

    @Column(value = "latitude")
    private String latitude; // 위도

    @Column(value = "longitude")
    private String longitude; // 경도
}
