package click.bitbank.api.presentation.card;

import click.bitbank.api.application.card.CardApplicationService;
import click.bitbank.api.application.response.CardListResponse;
import click.bitbank.api.application.response.dto.CardDTO;
import click.bitbank.api.domain.model.card.CardBenefitType;
import click.bitbank.api.infrastructure.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.test.StepVerifier;

import static click.bitbank.api.infrastructure.factory.CardTestFactory.cardListResponse;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebFluxTest(CardHandler.class)
class CardHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private GlobalExceptionHandler globalExceptionHandler;
    @MockBean
    private CardApplicationService cardApplicationService;

    /**
     * 인기 카드 목록 조회
     */
    @Test
    void cardPopularList() {
        // given
        given(cardApplicationService.findCardPopularList(any(ServerRequest.class))).willReturn(cardListResponse());

        // when
        FluxExchangeResult<CardListResponse> result = webClient
            .get()
            .uri("/card/popular-list")
            .exchange()
            .expectStatus().isOk()
            .returnResult(CardListResponse.class);

        // then
        verify(cardApplicationService).findCardPopularList(any(ServerRequest.class));

        StepVerifier.create(result.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());

                CardDTO cardDTO = response.getCardDTOList().get(0);
                assertEquals(1, cardDTO.getCardId());
                assertEquals("카드사", cardDTO.getCardCompany());
                assertEquals(CardBenefitType.B, cardDTO.getCardBenefitType());
                assertEquals("혜택 내용", cardDTO.getCardBenefitContent());
                assertEquals("이미지 경로", cardDTO.getCardImagePath());
                assertEquals(1, cardDTO.getCardRanking());
            }))
            .verifyComplete();
    }

    /**
     * 설문 조사에 따른 카드 추천
     */
    @Test
    void cardRecommendationList() {
        // given
        given(cardApplicationService.findCardRecommendationList(any(ServerRequest.class))).willReturn(cardListResponse());

        // when
        FluxExchangeResult<CardListResponse> result = webClient
            .get()
            .uri("/card/recommendation-list")
            .exchange()
            .expectStatus().isOk()
            .returnResult(CardListResponse.class);

        // then
        verify(cardApplicationService).findCardRecommendationList(any(ServerRequest.class));

        StepVerifier.create(result.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());

                CardDTO cardDTO = response.getCardDTOList().get(0);
                assertEquals(1, cardDTO.getCardId());
                assertEquals("카드사", cardDTO.getCardCompany());
                assertEquals(CardBenefitType.B, cardDTO.getCardBenefitType());
                assertEquals("혜택 내용", cardDTO.getCardBenefitContent());
                assertEquals("이미지 경로", cardDTO.getCardImagePath());
                assertEquals(1, cardDTO.getCardRanking());
            }))
            .verifyComplete();
    }
}