package click.bitbank.api.presentation.member;

import click.bitbank.api.application.response.MemberLoginResponse;
import click.bitbank.api.application.response.MemberSignupResponse;
import click.bitbank.api.presentation.member.request.MemberSignupRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import click.bitbank.api.infrastructure.config.WebFluxRouterConfig;
import click.bitbank.api.application.response.MemberInfoResponse;

@SpringBootTest
class MemberHandlerTest {

    private WebTestClient webTestClientPostMethod;
    private WebTestClient webTestClientGetMethod;

    @Autowired
    private WebFluxRouterConfig webFluxRouterConfig;
    @Autowired
    private MemberHandler memberHandler;

    @BeforeEach
    void setUp() {
        webTestClientPostMethod = WebTestClient
            .bindToRouterFunction( // WebFluxConfig에서 작성한 router를 WebTestClient에 바인딩해준다.
                webFluxRouterConfig.memberRouterBuilder(memberHandler)
            )
            .build();

        webTestClientGetMethod = WebTestClient
            .bindToRouterFunction( // WebFluxConfig에서 작성한 router를 WebTestClient에 바인딩해준다.
                webFluxRouterConfig.memberRouterGETBuilder(memberHandler)
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

        webTestClientPostMethod
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

        webTestClientPostMethod
            .post()
            .uri("/auth/login")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(MemberLoginResponse.class);
    }

    /**
     * 회원 정보 조회
     */
    @Test
    void findMemberInfo() {

        webTestClientGetMethod
            .get()
            .uri("/member/findMemberInfo/1")
            .exchange()
            .expectStatus().isOk()
            .expectBody(MemberInfoResponse.class);
    }
}