package click.bitbank.api.infrastructure.factory;

import click.bitbank.api.application.response.CardPopularListResponse;
import click.bitbank.api.application.response.dto.CardDTO;
import click.bitbank.api.domain.model.card.Card;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CardResponseFactory {
    
    private final ModelMapper modelMapper;
    
    /**
     * 인기 카드 목록 결과 구성
     * @param cardFlux : 카드 목록
     * @return 구성된 결과 반환
     */
    public Mono<CardPopularListResponse> cardPopularListResponseBuilder(Flux<Card> cardFlux) {
    
        return cardFlux // 카드 순위에 따라 목록 조회
            .collectList()
            .map(cardList -> {
                List<CardDTO> cardDTOList = cardList.stream()
                    .map(card ->
                        modelMapper.map(card, CardDTO.class)
                    )
                    .collect(Collectors.toList());
            
                return new CardPopularListResponse(cardDTOList);

            }).switchIfEmpty(Mono.just(new CardPopularListResponse()));
    }
}
