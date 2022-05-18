package click.bitbank.api.infrastructure.factory;

import click.bitbank.api.application.response.AccountBookStatisticResponse;
import click.bitbank.api.application.response.AccountBookWriteResponse;
import click.bitbank.api.application.response.DTO.DonutGraphDTO;
import click.bitbank.api.application.response.DTO.WeeklyTotalDTO;
import click.bitbank.api.domain.accountBook.model.AccountBookType;
import click.bitbank.api.domain.accountBook.model.income.IncomeType;
import click.bitbank.api.presentation.accountBook.request.AccountBookWriteRequest;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class AccountBookTestFactory {

    /**
     * 가계부 작성하기 Response 구성
     */
    public static Mono<AccountBookWriteResponse> accountBookWriteResponse() {

        return Mono.just(
            AccountBookWriteResponse.builder()
                .accountBookId(1)
                .build()
        );
    }

    /**
     * 가계부 작성하기 Request 구성
     */
    public static AccountBookWriteRequest accountBookWriteRequest() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return AccountBookWriteRequest.builder()
            .memberId(1)
            .accountBookType(AccountBookType.I)
            .incomeType(IncomeType.S)
            .accountName("회사")
            .price(BigInteger.valueOf(90000000))
            .createdDate(dateFormat.format(new Date()))
            .build();
    }

    /**
     * 월 별 지출/수입 통계 Response 구성
     */
    public static Mono<AccountBookStatisticResponse> accountBookStatisticResponse() {

        return Mono.just(
            AccountBookStatisticResponse.builder()
                .monthlyTotal(12341234L)
                .weeklyTotalDTOList(
                    Arrays.asList(
                        WeeklyTotalDTO.builder()
                            .week("5월 1주차")
                            .total(1234L)
                            .build(),
                        WeeklyTotalDTO.builder()
                            .week("5월 2주차")
                            .total(5678L)
                            .build()
                    )
                )
                .donutGraphDTOList(
                    Arrays.asList(
                        DonutGraphDTO.builder()
                            .id(1)
                            .name("FE")
                            .quantity(50)
                            .percentage(50)
                            .build(),
                        DonutGraphDTO.builder()
                            .id(2)
                            .name("BE")
                            .quantity(50)
                            .percentage(50)
                            .build()
                    )
                )
                .lineGraphDailyTotalList(Arrays.asList(1L, 2L, 3L, 4L))
                .lineGraphDayList(Arrays.asList("05.01", "05.02", "05.03", "05.04"))
                .build()
        );
    }
}
