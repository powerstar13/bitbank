package click.bitbank.api.application.card;

import click.bitbank.api.application.response.CardListResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface CardApplicationService {

    Mono<CardListResponse> findCardPopularList(); // 인기 카드 목록 조회

    Mono<CardListResponse> findCardRecommendationList(ServerRequest request); // 소비 패턴 설문에 따른 카드 추천
}