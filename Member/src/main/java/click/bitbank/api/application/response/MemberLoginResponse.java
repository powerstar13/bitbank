package click.bitbank.api.application.response;

import click.bitbank.api.domain.model.member.MemberType;
import lombok.*;
import click.bitbank.api.presentation.shared.response.SuccessResponse;


@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginResponse extends SuccessResponse {

    private int memberId; // 회원 고유번호

    private String memberName; // 회원 이름

    private MemberType memberType; // 회원 유형

    private String accessToken; // 액세스토큰

    private String refreshToken; // 리프레시토큰
}
