package click.bitbank.api.presentation.card;

import click.bitbank.api.application.response.CardPopularListResponse;
import click.bitbank.api.infrastructure.config.WebFluxRouterConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class CardHandlerTest {

    private WebTestClient webTestClientethod;

    @Autowired
    private WebFluxRouterConfig webFluxRouterConfig;
    @Autowired
    private CardHandler cardHandler;

    @BeforeEach
    void setUp() {
        webTestClientethod = WebTestClient
            .bindToRouterFunction( // WebFluxConfig에서 작성한 router를 WebTestClient에 바인딩해준다.
                webFluxRouterConfig.cardRouterBuilder(cardHandler)
            )
            .build();
    }

    /**
     * 인기 카드 목록 조회
     */
    @Test
    void cardPopularList() {
    
        webTestClientethod
            .get()
            .uri("/card/popular-list")
            .exchange()
            .expectStatus().isOk()
            .expectBody(CardPopularListResponse.class);
    }
}