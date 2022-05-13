package click.bitbank.api.application.card;

import click.bitbank.api.application.response.CardPopularListResponse;
import reactor.core.publisher.Mono;

public interface CardApplicationService {

    Mono<CardPopularListResponse> findCardPopularList(); // 인기 카드 목록 조회
}