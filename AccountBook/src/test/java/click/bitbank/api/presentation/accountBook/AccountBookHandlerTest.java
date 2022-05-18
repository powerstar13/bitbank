package click.bitbank.api.presentation.accountBook;

import click.bitbank.api.application.accountBook.AccountBookApplicationService;
import click.bitbank.api.application.response.AccountBookStatisticResponse;
import click.bitbank.api.application.response.AccountBookWriteResponse;
import click.bitbank.api.infrastructure.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.test.StepVerifier;

import static click.bitbank.api.infrastructure.factory.AccountBookTestFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebFluxTest(value = AccountBookHandler.class)
class AccountBookHandlerTest {
    
    @Autowired
    private WebTestClient webClient;
    
    @MockBean
    private GlobalExceptionHandler globalExceptionHandler;
    @MockBean
    private AccountBookApplicationService accountBookApplicationService;

    /**
     * 가계부 작성
     */
    @Test
    void accountBookWrite() {
        // given
        given(accountBookApplicationService.accountBookWrite(any(ServerRequest.class))).willReturn(accountBookWriteResponse());

        // when
        FluxExchangeResult<AccountBookWriteResponse> result = webClient
            .post()
            .uri("/account-book/write")
            .bodyValue(accountBookWriteRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .returnResult(AccountBookWriteResponse.class);

        // then
        verify(accountBookApplicationService).accountBookWrite(any(ServerRequest.class));

        StepVerifier.create(result.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.CREATED.value(), response.getRt());
                assertInstanceOf(Integer.class, response.getAccountBookId());
            }))
            .verifyComplete();
    }
    
    /**
     * 월 별 지출 통계
     */
    @Test
    void accountBookStatisticExpenditure() {
        // given
        given(accountBookApplicationService.accountBookStatistic(any(ServerRequest.class))).willReturn(accountBookStatisticResponse());
        
        // when
        FluxExchangeResult<AccountBookStatisticResponse> result = webClient
            .get()
            .uri(uriBuilder ->
                uriBuilder.path("/account-book/statistic/expenditure")
                    .queryParam("memberId", "2")
                    .build()
            )
            .exchange()
            .expectStatus().isOk()
            .returnResult(AccountBookStatisticResponse.class);
        
        // then
        verify(accountBookApplicationService).accountBookStatistic(any(ServerRequest.class));
        
        StepVerifier.create(result.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertEquals(12341234L, response.getMonthlyTotal());
            }))
            .verifyComplete();
    }

    /**
     * 월 별 수입 통계
     */
    @Test
    void accountBookStatisticIncome() {
        // given
        given(accountBookApplicationService.accountBookStatistic(any(ServerRequest.class))).willReturn(accountBookStatisticResponse());
        
        // when
        FluxExchangeResult<AccountBookStatisticResponse> result = webClient
            .get()
            .uri(uriBuilder ->
                uriBuilder.path("/account-book/statistic/income")
                    .queryParam("memberId", "2")
                    .build()
            )
            .exchange()
            .expectStatus().isOk()
            .returnResult(AccountBookStatisticResponse.class);
        
        // then
        verify(accountBookApplicationService).accountBookStatistic(any(ServerRequest.class));
        
        StepVerifier.create(result.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertEquals(12341234L, response.getMonthlyTotal());
            }))
            .verifyComplete();
    }
}