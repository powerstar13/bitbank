package click.bitbank.api.presentation.card.request;

import click.bitbank.api.presentation.shared.request.RequestVerify;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CardRecommendationListRequest implements RequestVerify {

    private Long consumptionAmountCost; // 월 간 사용 금액

    private Long transportationCost; // 교통비
    private Long phoneCost; // 통신비
    private Long eatCost; // 외식비
    private Long drinkCost; // 카페비
    private Long shoppingCost; // 쇼핑비
    private Long oilCost; // 주유비
    private Long bookCost; // 도서비
    private Long insuranceCost; // 보험비
    private Long marketCost; // 대형마트비
    private Long convenienceStoreCost; // 편의점비

    @Override
    public void verify() {
    }
}
