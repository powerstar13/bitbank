package click.bitbank.api.presentation.card;

import click.bitbank.api.application.card.CardApplicationService;
import click.bitbank.api.application.response.CardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class CardHandler {

    private final CardApplicationService cardApplicationService;

    /**
     * 인기 카드 목록 조회
     * @return Mono<ServerResponse> : CardListResponse
     */
    public Mono<ServerResponse> cardPopularList(ServerRequest request) {
    
        Mono<CardListResponse> response = cardApplicationService.findCardPopularList()
            .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, CardListResponse.class);
    }

    /**
     * 소비 패턴 설문에 따른 카드 추천
     * @return Mono<ServerResponse> : CardListResponse
     */
    public Mono<ServerResponse> cardRecommendationList(ServerRequest request) {

        Mono<CardListResponse> response = cardApplicationService.findCardRecommendationList(request)
            .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, CardListResponse.class);
    }

}
