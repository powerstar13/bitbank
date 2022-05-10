package click.bitbank.api.domain.model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum MemberType {

    S("S", "소셜 회원"),
    N("N", "일반 회원");

    private String name;
    private String description;
}
