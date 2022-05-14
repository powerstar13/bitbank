package click.bitbank.api.application.member;

import click.bitbank.api.application.response.AlarmCountResponse;
import click.bitbank.api.application.response.AlarmListResponse;
import click.bitbank.api.application.response.MemberLoginResponse;
import click.bitbank.api.application.response.MemberSignupResponse;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface MemberApplicationService {

    Mono<MemberSignupResponse> signup(ServerRequest request); // 회원 가입

    Mono<MemberLoginResponse> login(ServerRequest request); // 로그인

    Mono<SuccessResponse> logout(ServerRequest request); // 로그아웃

    Mono<SuccessResponse> delete(ServerRequest request); // 회원 탈퇴

    Mono<AlarmCountResponse> findAlarmCount(ServerRequest request); // 읽지 않은 알림 갯수 조회

    Mono<AlarmListResponse> findAlarmList(ServerRequest request); // 읽지 않은 알림 목록 조회

}