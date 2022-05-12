package click.bitbank.api.domain.accountBook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum IncomeType {

    S("S", "급여"),
    A("A", "용돈"),
    F("F", "금융 수입"),
    B("B", "사업 수입"),
    O("O", "기타 수입");

    private String name;
    private String description;
}
