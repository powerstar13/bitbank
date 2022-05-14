package click.bitbank.api.infrastructure.factory;

import click.bitbank.api.application.response.AlarmCountResponse;
import click.bitbank.api.application.response.AlarmListResponse;
import click.bitbank.api.application.response.MemberLoginResponse;
import click.bitbank.api.application.response.MemberSignupResponse;
import click.bitbank.api.domain.model.member.MemberType;
import click.bitbank.api.presentation.member.request.MemberLoginRequest;
import click.bitbank.api.presentation.member.request.MemberSignupRequest;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;

public class MemberTestFactory {

    /**
     * 일반 회원 가입 Response 구성
     */
    public static Mono<MemberSignupResponse> memberSignupResponse() {

        return Mono.just(
            MemberSignupResponse.builder()
                .memberId(1)
                .build()
        );
    }

    /**
     * 일반 회원 가입 Request 구성
     */
    public static MemberSignupRequest memberSignupRequest() {

        return MemberSignupRequest.builder()
            .memberLoginId("gildong123")
            .memberName("홍길동")
            .memberPassword("1234")
            .build();
    }

    /**
     * 일반 회원 로그인 Response 구성
     */
    public static Mono<MemberLoginResponse> memberLoginResponse() {

        return Mono.just(
            MemberLoginResponse.builder()
                .memberId(1)
                .memberName("강보경")
                .accessToken("액세스토큰")
                .refreshToken("리프레시토큰")
                .memberType(MemberType.N)
                .build()
        );
    }

    /**
     * 일반 회원 로그인 Request 구성
     */
    public static MemberLoginRequest memberLoginRequest() {

        return MemberLoginRequest.builder()
            .memberLoginId("boookk")
            .memberPassword("boookk")
            .build();
    }

    /**
     * 읽지 않은 알림 갯수 Response 구성
     */
    public static Mono<AlarmCountResponse> alarmCountResponse() {
        return Mono.just(
            AlarmCountResponse.builder()
                .alarmCount(1)
                .build()
        );
    }

    /**
     * 읽지 않은 알림 메시지 목록
     */
    public static ArrayList<String> alarmMessageList() {
        return new ArrayList<>(Arrays.asList("메시지1", "메시지2"));
    }

    /**
     * 읽지 않은 알림 메시지 Response 구성
     */
    public static Mono<AlarmListResponse> alarmListResponse() {

        return Mono.just(
            AlarmListResponse.builder()
                .alarmMessageList(alarmMessageList())
                .build()
        );
    }
}
