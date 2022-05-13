package click.bitbank.api.application.response;

import click.bitbank.api.application.response.dto.CardDTO;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardPopularListResponse extends SuccessResponse {

    private List<CardDTO> cardDTOList; // 인기 카드 목록
}
