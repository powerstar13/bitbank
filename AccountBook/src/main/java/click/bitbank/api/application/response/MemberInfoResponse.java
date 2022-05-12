package click.bitbank.api.application.response;

import click.bitbank.api.domain.member.MemberType;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse extends SuccessResponse {

    private int memberId; // 회원 고유번호

    private MemberType memberType;
}
