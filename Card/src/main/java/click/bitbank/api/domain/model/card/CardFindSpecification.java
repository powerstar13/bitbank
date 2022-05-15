package click.bitbank.api.domain.model.card;

import click.bitbank.api.application.response.CardListResponse;
import click.bitbank.api.infrastructure.factory.CardResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CardFindSpecification {

    private final CardRepository cardRepository;
    private final CardResponseFactory cardResponseFactory;

    /**
     * 인기 카드 목록 조회
     * @return Flux<Card> : 인기 카드 목록
     */
    public Flux<Card> cardPopularList() {
        return cardRepository.findTop10ByCardRankingIsNotNullOrderByCardRankingAsc(); // 카드 순위에 따라 Top 10 목록 조회
    }

    /**
     * 소비 패턴 설문에 따른 카드 추천 처리
     * @param request : 소비 패턴 설문
     * @return Mono<CardListResponse> : 카드 추천 목록
     */
    public Mono<CardListResponse> cardRecommendationVerify(Map<String, String> request) {

        return cardResponseFactory.cardListResponseBuilder(
            Flux.fromIterable(new ArrayList<>(request.entrySet()))
                .sort((o1, o2) -> {
                    // 카테고리 별 비용 높은 순서대로 정렬
                    String o1Val = o1.getValue() == null ? "0" : o1.getValue();
                    String o2Val = o2.getValue() == null ? "0" : o2.getValue();

                    return o2Val.compareTo(o1Val);
                })
                .filter(entry -> !entry.getKey().equals("consumptionAmountCost")) // 월 평균 사용 금액은 카테고리 별 비용에서 제외 처리
                .concatMap(entry ->
                    cardRepository.findFirstByCardBenefitType( // 정렬된 카테고리에 매칭되는 카드 조회
                        CardBenefitType.valueOf(entry.getKey().toUpperCase().substring(0, 1))
                    )
                )
                .retry() // 비동기로 여러 데이터를 조회하는 동안 끊기지 않도록 재시도하여 성공적으로 조회할 수 있도록 처리
                .switchIfEmpty(this.cardPopularList()) // 조회할 조건이 없을 경우 인기 순위대로 카드 10순위 조회
        );
    }
}
