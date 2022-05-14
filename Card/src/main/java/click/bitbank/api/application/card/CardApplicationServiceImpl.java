package click.bitbank.api.application.card;

import click.bitbank.api.application.response.CardPopularListResponse;
import click.bitbank.api.domain.model.card.Card;
import click.bitbank.api.domain.model.card.CardRepository;
import click.bitbank.api.infrastructure.factory.CardResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CardApplicationServiceImpl implements CardApplicationService {

    private final CardRepository cardRepository;
    private final CardResponseFactory cardResponseFactory;

    /**
     * 인기 카드 목록 조회
     * @return Mono<CardPopularListResponse> : 인기 카드 목록
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<CardPopularListResponse> findCardPopularList() {
    
        Flux<Card> cardFlux = cardRepository.findTop10ByCardRankingIsNotNullOrderByCardRankingAsc(); // 카드 순위에 따라 Top 10 목록 조회

        return cardResponseFactory.cardPopularListResponseBuilder(cardFlux);
    }
}
