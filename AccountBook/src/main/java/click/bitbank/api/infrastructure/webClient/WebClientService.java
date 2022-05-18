package click.bitbank.api.infrastructure.webClient;

import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import click.bitbank.api.presentation.shared.response.CommonResponse;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class WebClientService {

    @Value("${msa.client.url.member}")
    private String memberUrl;

    /**
     * 회원 유효성 검증
     * @param memberId : 회원 고유번호
     * @return Mono<MemberVerifyResponse> : 회원 검증 결과
     */
    public Mono<CommonResponse> callMemberVerify(int memberId) {

        return WebClient.create(memberUrl).get()
            .uri("/member/exist-verify?memberId=" + memberId)
            .retrieve()
            .onStatus(HttpStatus::is5xxServerError,
                response -> Mono.error(new RuntimeException("서버에 문제가 생겼습니다. 관리자에게 문의 바랍니다."))
            )
            .onStatus(HttpStatus::is4xxClientError,
                response -> response.bodyToMono(CommonResponse.class)
                    .flatMap(commonResponse -> Mono.error(new BadRequestException(commonResponse.getRtMsg())))
            )
            .bodyToMono(CommonResponse.class);
    }
}
