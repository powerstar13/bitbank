package click.bitbank.api.domain.accountBook.model.transfer;

import click.bitbank.api.domain.accountBook.AccountBookType;
import click.bitbank.api.domain.accountBook.model.Classification;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "transfer")
public class Transfer extends Classification {

    @Id
    @Column(value = "transferId")
    private int transferId; // 이체 번호

    @Column(value = "transferInfo")
    private String transferInfo; // 이체 내역

    @Column(value = "transferDate")
    private LocalDateTime transferDate; // 지출 날짜

    @Column(value = "transferMoney")
    private BigInteger transferMoney; // 이체 금액

    @Column(value = "transferType")
    private TransferType transferType; // 이체 유형

    @Column(value = "memberId")
    private int memberId;

    public AccountBookType getAccountBookType() {
        return AccountBookType.T;
    }
    public BigInteger getNegateExpenditureMoney() {
        return transferMoney.negate();
    }
}
