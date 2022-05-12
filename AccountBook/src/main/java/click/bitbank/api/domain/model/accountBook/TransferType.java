package click.bitbank.api.domain.model.accountBook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum TransferType {

    A("A", "내 계좌로 이체"),
    T("T", "이체"),
    C("C", "카드 대금"),
    D("D", "저축"),
    M("M", "현금"),
    S("S", "투자"),
    L("L", "대출"),
    I("I", "보험");

    private String name;
    private String description;
}
