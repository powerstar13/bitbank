package click.bitbank.api.infrastructure.factory;

import click.bitbank.api.application.response.CardPopularListResponse;
import click.bitbank.api.application.response.dto.CardDTO;
import click.bitbank.api.domain.model.card.CardBenefitType;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

public class CardTestFactory {

    /**
     * 인기 카드 목록 Response 구성
     */
    public static Mono<CardPopularListResponse> cardPopularListResponse() {

        ArrayList<CardDTO> cardDTOList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            cardDTOList.add(
                CardDTO.builder()
                    .cardId(i)
                    .cardCompany("카드사")
                    .cardBenefitType(CardBenefitType.B)
                    .cardBenefitContent("혜택 내용")
                    .cardImagePath("이미지 경로")
                    .cardRanking(i)
                    .build()
            );
        }

        return Mono.just(
            CardPopularListResponse.builder()
                .cardDTOList(cardDTOList)
                .build()
        );
    }
}
