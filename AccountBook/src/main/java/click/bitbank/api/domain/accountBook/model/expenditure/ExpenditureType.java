package click.bitbank.api.domain.accountBook.model.expenditure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExpenditureType {

    E("E", "식비"),
    L("L", "생활"),
    S("S", "쇼핑"),
    B("B", "뷰티/미용"),
    T("T", "교통"),
    C("C", "자동차"),
    U("U", "주거/통신"),
    M("M", "의료/건강"),
    F("F", "금융"),
    A("A", "문화/여가"),
    E2("E2", "교육/학습"),
    D("D", "자녀/육아"),
    P("P", "반려동물"),
    G("G", "경조/선물");

    private String name;
    private String description;
}
