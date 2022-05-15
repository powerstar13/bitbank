package click.bitbank.api.domain.accountBook.model;

import click.bitbank.api.domain.accountBook.TransferType;
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
@Table(value = "transfer")
public class Transfer {

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

    @Column(value = "regDate")
    @CreatedDate
    private LocalDateTime regDate; // 생성일

    @Column(value = "modDate")
    @LastModifiedDate
    private LocalDateTime modDate; // 수정일
}
