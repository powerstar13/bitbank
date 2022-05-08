package click.bitbank.api.application.response;

import lombok.*;
import click.bitbank.api.presentation.shared.response.CreatedSuccessResponse;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegistrationResponse extends CreatedSuccessResponse {

    private int memberId; // 회원 고유번호
}
