package click.bitbank.api.application.response.DTO;

import click.bitbank.api.domain.accountBook.AccountBookType;
import click.bitbank.api.domain.accountBook.model.expenditure.ExpenditureType;
import click.bitbank.api.domain.accountBook.model.income.IncomeType;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DonutGraphDTO {

    private int id; // 카테고리 별 순서

    private String name; // 카테고리명

    private long quantity; // 카테고리 별 총 금액

    private int percentage; // 카테고리 별 비중

    public void setName(AccountBookType accountBookType, String name) {
        this.name = accountBookType.equals(AccountBookType.P) ? ExpenditureType.valueOf(name).getDescription() : IncomeType.valueOf(name).getDescription();
    }
}
