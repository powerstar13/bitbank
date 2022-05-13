package click.bitbank.api.application.response.dto;

import click.bitbank.api.domain.model.card.CardBenefitType;
import lombok.*;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    
    private int cardId; // 카드 고유번호
    
    private String cardCompany; // 카드사
    
    private String cardName; // 카드명
    
    private CardBenefitType cardBenefitType; // 카드 혜택 종류
    
    private String cardBenefitContent; // 카드 혜택 내용
    
    private int cardRanking; // 카드 순위
    
    private String cardImagePath; // 카드 이미지 경로
}
