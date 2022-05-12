package click.bitbank.api.domain.accountBook.model;

import click.bitbank.api.domain.accountBook.TransferType;
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
@Table(value = "transfer")
public class Transfer {

    @Id
    @Column(value = "transferId")
    private int transferId; // 이체 번호

    @Column(value = "transferInfo")
    private String transferInfo; // 이체 내역

    @Column(value = "transferDate")
    private Timestamp transferDate; // 지출 날짜

    @Column(value = "transferMoney")
    private BigInteger transferMoney; // 이체 금액

    @Column(value = "transferType")
    private TransferType transferType; // 이체 유형

    @Column(value = "regDate")
    private Timestamp regDate; // 생성일

    @Column(value = "modDate")
    private Timestamp modDate; // 수정일
}
