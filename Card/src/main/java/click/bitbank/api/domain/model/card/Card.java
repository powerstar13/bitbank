package click.bitbank.api.domain.model.card;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "card")
public class Card {

    @Id // ID로 엔티티를 식별한다.
    @Column(value = "cardId")
    private int cardId; // 카드 고유번호
    
    @Column(value = "cardCompany")
    private String cardCompany; // 카드사
    
    @Column(value = "cardName")
    private String cardName; // 카드명
    
    @Column(value = "cardBenefitType")
    private CardBenefitType cardBenefitType; // 카드 혜택 종류
    
    @Column(value = "cardBenefitContent")
    private String cardBenefitContent; // 카드 혜택 내용
    
    @Column(value = "cardRanking")
    private int cardRanking; // 카드 순위
    
    @Column(value = "cardImagePath")
    private String cardImagePath; // 카드 이미지 경로

    @Column(value = "regDate")
    @CreatedDate
    private LocalDateTime regDate; // 생성일

    @Column(value = "modDate")
    @LastModifiedDate
    private LocalDateTime modDate; // 수정일
}
