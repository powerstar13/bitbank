package click.bitbank.api.presentation.member;

import click.bitbank.api.application.member.MemberApplicationService;
import click.bitbank.api.application.response.AlarmCountResponse;
import click.bitbank.api.application.response.AlarmListResponse;
import click.bitbank.api.application.response.MemberLoginResponse;
import click.bitbank.api.application.response.MemberSignupResponse;
import click.bitbank.api.domain.model.member.MemberType;
import click.bitbank.api.infrastructure.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.test.StepVerifier;

import static click.bitbank.api.infrastructure.factory.MemberTestFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebFluxTest(MemberHandler.class)
class MemberHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private GlobalExceptionHandler globalExceptionHandler;
    @MockBean
    private MemberApplicationService memberApplicationService;

    /**
     * 일반 회원 등록
     */
    @Test
    void signup() {
        // given
        given(memberApplicationService.signup(any(ServerRequest.class))).willReturn(memberSignupResponse());

        // when
        FluxExchangeResult<MemberSignupResponse> result = webClient
            .post()
            .uri("/auth/signup")
            .bodyValue(memberSignupRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .returnResult(MemberSignupResponse.class);

        // then
        verify(memberApplicationService).signup(any(ServerRequest.class));

        StepVerifier.create(result.getResponseBody().log())
            .assertNext(response -> {
                assertAll(() -> {
                    assertEquals(HttpStatus.CREATED.value(), response.getRt());
                    assertInstanceOf(Integer.class, response.getMemberId());
                });
            })
            .verifyComplete();
    }

    /**
     * 로그인
     */
    @Test
    void login() {
        // given
        given(memberApplicationService.login(any(ServerRequest.class))).willReturn(memberLoginResponse());

        // when
        FluxExchangeResult<MemberLoginResponse> result = webClient
            .post()
            .uri("/auth/login")
            .bodyValue(memberLoginRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .returnResult(MemberLoginResponse.class);

        // then
        verify(memberApplicationService).login(any(ServerRequest.class));

        StepVerifier.create(result.getResponseBody().log())
            .assertNext(response -> {
                assertAll(() -> {
                    assertEquals(HttpStatus.OK.value(), response.getRt());
                    assertEquals(1, response.getMemberId());
                    assertEquals("강보경", response.getMemberName());
                    assertEquals(MemberType.N, response.getMemberType());
                });
            })
            .verifyComplete();
    }

    /**
     * 읽지 않은 알림 갯수 조회
     */
    @Test
    void alarmCount() {
        // given
        given(memberApplicationService.findAlarmCount(any(ServerRequest.class))).willReturn(alarmCountResponse());

        // when
        FluxExchangeResult<AlarmCountResponse> result = webClient
            .get()
            .uri(uriBuilder ->
                uriBuilder.path("/member/alarm-count")
                    .queryParam("memberId", "2")
                    .build()
            )
            .exchange()
            .expectStatus().isOk()
            .returnResult(AlarmCountResponse.class);

        // then
        verify(memberApplicationService).findAlarmCount(any(ServerRequest.class));

        StepVerifier.create(result.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertEquals(1, response.getAlarmCount());
            }))
            .verifyComplete();
    }

    /**
     * 읽지 않은 알림 목록 조회
     */
    @Test
    void alarmList() {
        // given
        given(memberApplicationService.findAlarmList(any(ServerRequest.class))).willReturn(alarmListResponse());

        // when
        FluxExchangeResult<AlarmListResponse> result = webClient
            .get()
            .uri(uriBuilder ->
                uriBuilder.path("/member/alarm-list")
                    .queryParam("memberId", "2")
                    .build()
            )
            .exchange()
            .expectStatus().isOk()
            .returnResult(AlarmListResponse.class);

        // then
        verify(memberApplicationService).findAlarmList(any(ServerRequest.class));

        StepVerifier.create(result.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertArrayEquals(alarmMessageList().toArray(), response.getAlarmMessageList().toArray());
            }))
            .verifyComplete();
    }
}