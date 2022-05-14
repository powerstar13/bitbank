package click.bitbank.api.application.member;

import click.bitbank.api.application.response.AlarmCountResponse;
import click.bitbank.api.application.response.AlarmListResponse;
import click.bitbank.api.application.response.MemberLoginResponse;
import click.bitbank.api.application.response.MemberSignupResponse;
import click.bitbank.api.domain.model.alarm.AlarmFindSpecification;
import click.bitbank.api.domain.model.member.MemberFindSpecification;
import click.bitbank.api.domain.model.member.MemberLoginSpecification;
import click.bitbank.api.domain.model.member.MemberSaveSpecification;
import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.presentation.member.request.MemberLoginRequest;
import click.bitbank.api.presentation.member.request.MemberSignupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberApplicationServiceImpl implements MemberApplicationService {

    private final MemberSaveSpecification memberSaveSpecification;
    private final MemberLoginSpecification memberLoginSpecification;
    private final MemberFindSpecification memberFindSpecification;
    private final AlarmFindSpecification alarmFindSpecification;

    /**
     * 회원 계정 생성
     * @param serverRequest : 전달된 Request
     * @return Mono<MemberRegistrationResponse> : 저장된 회원 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<MemberSignupResponse> signup(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(MemberSignupRequest.class).flatMap(
            request -> {
                request.verify(); // Request 유효성 검사

                return memberSaveSpecification.memberExistCheckAndRegistration(request); // 회원 계정 생성
            }
        ).switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())));
    }

    /**
     * 일반 회원 로그인
     * @param serverRequest : 전달된 Request
     * @return Mono<MemberLoginResponse> : 저장된 회원 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<MemberLoginResponse> login(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(MemberLoginRequest.class).flatMap(
                request -> {
                    request.verify(); // Request 유효성 검사
                    return memberLoginSpecification.memberExistCheckAndLogin(request);
                }
        ).switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())));
    }

    /**
     * 회원 고유번호 추출
     * @param serverRequest : Params
     * @return int : 회원 고유번호
     */
    private int getMemberIdByRequest(ServerRequest serverRequest) {
        // 회원 고유번호 추출
        return Integer.parseInt(serverRequest.queryParam("memberId")
            .orElseThrow(() -> new BadRequestException(ExceptionMessage.IsRequiredMemberId.getMessage())));
    }

    /**
     * 읽지 않은 알림 갯수 조회
     * @param serverRequest : 전달된 Request
     * @return Mono<AlarmCountResponse> : 읽지 않은 알림 갯수
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<AlarmCountResponse> findAlarmCount(ServerRequest serverRequest) {
        // 회원 고유번호 추출
        int memberId = this.getMemberIdByRequest(serverRequest);

        return memberFindSpecification.membmerVerify(memberId) // 회원 정보 검증
            .flatMap(member ->
                alarmFindSpecification.unreadAlarmCountByMember(member.getMemberId())
            );
    }

    /**
     * 읽지 않은 알림 목록 조회
     * @param serverRequest : 전달된 Request
     * @return Mono<AlarmListResponse> : 읽지 않은 알림 목록
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<AlarmListResponse> findAlarmList(ServerRequest serverRequest) {
        // 회원 고유번호 추출
        int memberId = this.getMemberIdByRequest(serverRequest);

        return memberFindSpecification.membmerVerify(memberId) // 회원 정보 검증
            .flatMap(member ->
                alarmFindSpecification.unreadAlarmListByMember(member.getMemberId())
            );
    }

}
