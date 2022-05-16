package click.bitbank.api.application.response;

import click.bitbank.api.application.response.dto.CardDTO;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardListResponse extends SuccessResponse {

    private List<CardDTO> cardDTOList; // 카드 목록
}
