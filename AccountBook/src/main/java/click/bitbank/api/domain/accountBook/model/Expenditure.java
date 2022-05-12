package click.bitbank.api.domain.accountBook.model;

import click.bitbank.api.domain.accountBook.ExpenditureType;
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
@Table(value = "expenditure")
public class Expenditure {

    @Id
    @Column(value = "expenditureId")
    private int expenditureId; // 지출 번호

    @Column(value = "expenditureInfo")
    private String expenditureInfo; // 지출 내역

    @Column(value = "expenditureDate")
    private Timestamp expenditureDate; // 지출 날짜

    @Column(value = "expenditureMoney")
    private BigInteger expenditureMoney; // 지출 금액

    @Column(value = "expenditureType")
    private ExpenditureType expenditureType; // 지출 유형

    @Column(value = "latitude")
    private String latitude; // 위도

    @Column(value = "longitude")
    private String longitude; // 경도

    @Column(value = "regDate")
    private Timestamp regDate; // 생성일

    @Column(value = "modDate")
    private Timestamp modDate; // 수정일
}
