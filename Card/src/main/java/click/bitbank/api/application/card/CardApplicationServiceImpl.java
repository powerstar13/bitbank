package click.bitbank.api.application.card;

import click.bitbank.api.application.response.CardListResponse;
import click.bitbank.api.domain.model.card.CardFindSpecification;
import click.bitbank.api.infrastructure.factory.CardResponseFactory;
import click.bitbank.api.infrastructure.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CardApplicationServiceImpl implements CardApplicationService {

    private final CardResponseFactory cardResponseFactory;
    private final CardFindSpecification cardFindSpecification;
    private final KafkaProducerService kafkaProducerService;

    /**
     * 인기 카드 목록 조회
     * @return Mono<CardPopularListResponse> : 인기 카드 목록
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<CardListResponse> findCardPopularList(ServerRequest serverRequest) {
        kafkaProducerService.sendRequestTopic(serverRequest, serverRequest.queryParams().toSingleValueMap().toString());

        return cardResponseFactory.cardListResponseBuilder(cardFindSpecification.cardPopularList())
            .doOnSuccess(response -> kafkaProducerService.sendResponseTopic(serverRequest, response.toString()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<CardListResponse> findCardRecommendationList(ServerRequest serverRequest) {

        return cardFindSpecification.cardRecommendationVerify(serverRequest.queryParams().toSingleValueMap())
            .doOnSuccess(response -> kafkaProducerService.sendResponseTopic(serverRequest, response.toString()));
    }
}
