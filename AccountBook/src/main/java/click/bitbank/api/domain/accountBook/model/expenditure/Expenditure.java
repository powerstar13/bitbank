package click.bitbank.api.domain.accountBook.model.expenditure;

import click.bitbank.api.domain.accountBook.AccountBookType;
import click.bitbank.api.domain.accountBook.model.Classification;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "expenditure")
public class Expenditure extends Classification {

    @Id
    @NotNull(message = "지출 id는 필수값입니다.")
    @Column(value = "expenditureId")
    private int expenditureId; // 지출 번호

    @Column(value = "expenditureInfo")
    private String expenditureInfo; // 지출 내역

    @Column(value = "expenditureDate")
    private LocalDateTime expenditureDate; // 지출 날짜

    @Min(value = 1, message = "1원 이상 입력해야합니다.")
    @Column(value = "expenditureMoney")
    private BigInteger expenditureMoney; // 지출 금액

    @Column(value = "expenditureType")
    private ExpenditureType expenditureType; // 지출 유형

    @Column(value = "latitude")
    private String latitude; // 위도

    @Column(value = "longitude")
    private String longitude; // 경도

    @Column(value = "memberId")
    private int memberId;


    public AccountBookType getAccountBookType() {
        return AccountBookType.P;
    }
    public BigInteger getNegateExpenditureMoney() {
        return expenditureMoney.negate();
    }
//    /**
//     * DTO Mapping method
//     * 이름이 같은 필드명 끼리는 ModelMapper를 통해 mapping
//     * @param expenditureDTO : ExpenditureDTO
//     * @return
//     */
//    public static Expenditure of(ExpenditureDTO expenditureDTO) {
//
//       return ModelMapperUtils.getModelMapper().map(expenditureDTO, Expenditure.class);
//    }
}
