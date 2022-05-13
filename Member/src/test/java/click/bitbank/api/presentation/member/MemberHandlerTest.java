package click.bitbank.api.presentation.member;

import click.bitbank.api.application.member.response.MemberLoginResponse;
import click.bitbank.api.application.member.response.MemberSignupResponse;
import click.bitbank.api.infrastructure.config.WebFluxRouterConfig;
import click.bitbank.api.presentation.member.request.MemberLoginRequest;
import click.bitbank.api.presentation.member.request.MemberSignupRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class MemberHandlerTest {

    private WebTestClient webTestClientMethod;

    @Autowired
    private WebFluxRouterConfig webFluxRouterConfig;
    @Autowired
    private MemberHandler memberHandler;

    @BeforeEach
    void setUp() {
        webTestClientMethod = WebTestClient
            .bindToRouterFunction( // WebFluxConfig에서 작성한 router를 WebTestClient에 바인딩해준다.
                webFluxRouterConfig.memberRouterBuilder(memberHandler)
            )
            .build();
    }

    /**
     * 일반 회원 등록
     */
    @Test
    void signup() {

        MemberSignupRequest request = MemberSignupRequest.builder()
            .memberLoginId("gildong123")
            .memberName("홍길동")
            .memberPassword("1234")
            .build();

        webTestClientMethod
            .post()
            .uri("/auth/signup")
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(MemberSignupResponse.class)
            .value(memberSignupResponse -> {
                Assertions.assertInstanceOf(Integer.class, memberSignupResponse.getMemberId());
            });
    }

    /**
     * 로그인
     */
    @Test
    void login() {

        MemberLoginRequest request = MemberLoginRequest.builder()
            .memberLoginId("boookk")
            .memberPassword("boookk")
            .build();

        webTestClientMethod
            .post()
            .uri("/auth/login")
            .bodyValue(request)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(MemberLoginResponse.class);
    }
}