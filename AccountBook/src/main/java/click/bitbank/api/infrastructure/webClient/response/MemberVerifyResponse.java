package click.bitbank.api.infrastructure.webClient.response;

import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberVerifyResponse extends SuccessResponse {

    private boolean verify; // 검증 결과
}
