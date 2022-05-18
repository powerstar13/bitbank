package click.bitbank.api.domain.accountBook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum AccountBookType {

    I("I", "수입"),
    P("P", "지출"),
    T("T", "이체");

    private final String name;
    private final String description;
}
