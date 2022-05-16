package click.bitbank.api.presentation.member;

import click.bitbank.api.application.member.MemberApplicationService;
import click.bitbank.api.application.response.AlarmCountResponse;
import click.bitbank.api.application.response.AlarmListResponse;
import click.bitbank.api.application.response.MemberLoginResponse;
import click.bitbank.api.application.response.MemberSignupResponse;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
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
     * 소셜 로그인
     * @param request : 소셜 정보
     * @return Mono<ServerResponse> : 권한 인증 정보
     */
    public Mono<ServerResponse> socialLogin(ServerRequest request) {

        Mono<MemberLoginResponse> response = memberApplicationService.socialLogin(request)
            .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, MemberLoginResponse.class);
    }

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

    /**
     * 로그아웃
     * @param request : 회원 정보
     * @return Mono<ServerResponse> : 성공 여부
     */
    public Mono<ServerResponse> logout(ServerRequest request) {

        Mono<SuccessResponse> response = memberApplicationService.logout(request)
            .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, SuccessResponse.class);
    }

    /**
     * 회원 정보 수정
     * @param request : 수정할 회원 정보
     * @return Mono<ServerResponse> : 성공 여부
     */
    public Mono<ServerResponse> modification(ServerRequest request) {

        Mono<SuccessResponse> response = memberApplicationService.modification(request)
            .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, SuccessResponse.class);
    }

    /**
     * 회원 탈퇴
     * @param request : 회원 정보
     * @return Mono<ServerResponse> : 성공 여부
     */
    public Mono<ServerResponse> delete(ServerRequest request) {

        Mono<SuccessResponse> response = memberApplicationService.delete(request)
            .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, SuccessResponse.class);
    }
    
    /**
     * 읽지 않은 알림 갯수 조회
     * @param request : 회원 정보
     * @return Mono<ServerResponse> : 읽지 않은 알림 갯수
     */
    public Mono<ServerResponse> alarmCount(ServerRequest request) {
        
        Mono<AlarmCountResponse> response = memberApplicationService.findAlarmCount(request)
            .subscribeOn(Schedulers.boundedElastic());
        
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, AlarmCountResponse.class);
    }

    /**
     * 읽지 않은 알림 목록 조회
     * @param request : 회원 정보
     * @return Mono<ServerResponse> : 읽지 않은 알림 목록
     */
    public Mono<ServerResponse> alarmList(ServerRequest request) {

        Mono<AlarmListResponse> response = memberApplicationService.findAlarmList(request)
            .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, AlarmListResponse.class);
    }

    /**
     * 회원 검증
     * @param request : 회원 정보
     * @return Mono<ServerResponse> : 성공 여부
     */
    public Mono<ServerResponse> existVerify(ServerRequest request) {

        Mono<SuccessResponse> response = memberApplicationService.existVerify(request)
            .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, SuccessResponse.class);
    }

}
