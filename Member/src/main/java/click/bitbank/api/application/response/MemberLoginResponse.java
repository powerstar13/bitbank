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

    private String memberName;

    private MemberType memberType;

    private String accessToken;

    private String refreshToken;

}
