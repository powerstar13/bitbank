package click.bitbank.api.domain.model.accountBook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum SearchDateType {

    A("A", "전체"),
    W("W", "이번 주"),
    M("M", "이번 달"),
    Y("Y", "이번 년도"),
    M3("M3", "최근 3개월"),
    M6("M6", "최근 6개월"),
    S("S", "기간 설정");

    private String name;
    private String description;
}
