package click.bitbank.api.application.member;

import click.bitbank.api.application.response.AlimCountResponse;
import click.bitbank.api.application.response.MemberLoginResponse;
import click.bitbank.api.application.response.MemberSignupResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface MemberApplicationService {

    Mono<MemberSignupResponse> signup(ServerRequest request); // 회원 가입

    Mono<MemberLoginResponse> login(ServerRequest request); // 로그인
    
    Mono<AlimCountResponse> findAlimCount(ServerRequest request); // 읽지 않은 알림 갯수 조회

}