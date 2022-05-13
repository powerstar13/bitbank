package click.bitbank.api.application.member;

import click.bitbank.api.application.response.MemberLoginResponse;
import click.bitbank.api.application.response.MemberSignupResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface MemberApplicationService {

    Mono<MemberSignupResponse> signup(ServerRequest request); // 회원 가입

    Mono<MemberLoginResponse> login(ServerRequest request); // 로그인

}