package click.bitbank.api.presentation.card;

import click.bitbank.api.application.card.CardApplicationService;
import click.bitbank.api.application.response.CardPopularListResponse;
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
     * @return Mono<ServerResponse> : 인기 카드 목록
     */
    public Mono<ServerResponse> cardPopularList(ServerRequest request) {
    
        Mono<CardPopularListResponse> response = cardApplicationService.findCardPopularList()
            .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, CardPopularListResponse.class);
    }

}
