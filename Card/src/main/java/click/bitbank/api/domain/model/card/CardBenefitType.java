package click.bitbank.api.domain.model.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardBenefitType {

    T("T", "교통비"),
    P("P", "통신비"),
    E("E", "외식비"),
    D("D", "카페비"),
    S("S", "쇼핑비"),
    O("O", "주유비"),
    B("B", "도서비"),
    I("I", "보험비"),
    M("M", "대형마트비"),
    C("C", "편의점비");

    private String name;
    private String description;
}
