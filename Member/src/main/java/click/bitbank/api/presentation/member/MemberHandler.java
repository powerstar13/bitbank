package click.bitbank.api.presentation.member;

import click.bitbank.api.application.member.MemberApplicationService;
import click.bitbank.api.application.response.MemberLoginResponse;
import click.bitbank.api.application.response.MemberSignupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class MemberHandler {

    private final MemberApplicationService memberApplicationService;

    /**
     * 일반 회원 가입
     * @param request : 가입할 회원 정보
     * @return Mono<ServerResponse> : 등록한 회원 정보
     */
    public Mono<ServerResponse> signup(ServerRequest request) {

        Mono<MemberSignupResponse> response = memberApplicationService.signup(request)
            .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, MemberSignupResponse.class);
    }

    /**
     * 로그인
     * @param request : 로그인 정보
     * @return Mono<ServerResponse> : 권한 인증 정보
     */
    public Mono<ServerResponse> login(ServerRequest request) {

        Mono<MemberLoginResponse> response = memberApplicationService.login(request)
                .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, MemberLoginResponse.class);
    }

}
