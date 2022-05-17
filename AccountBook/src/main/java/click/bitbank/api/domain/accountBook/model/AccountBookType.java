package click.bitbank.api.domain.accountBook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum AccountBookType {

    I("I", "수입"),
    E("E", "지출"),
    T("T", "이체");

    private String name;
    private String description;
}
