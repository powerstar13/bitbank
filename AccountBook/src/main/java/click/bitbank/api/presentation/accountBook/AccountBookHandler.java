package click.bitbank.api.presentation.accountBook;

import click.bitbank.api.application.accountBook.AccountBookApplicationService;
import click.bitbank.api.application.response.AccountBookSearchResponse;
import click.bitbank.api.application.response.AccountBookStatisticResponse;
import click.bitbank.api.application.response.AccountBookWriteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountBookHandler {

    private final AccountBookApplicationService accountBookApplicationService;


    public Mono<ServerResponse> accountBookWrite(ServerRequest request) {
        Mono<Object> response = accountBookApplicationService.accountBookWrite(request)
                .subscribeOn(Schedulers.boundedElastic());

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, AccountBookWriteResponse.class);
    }

    /**
     * 가계부 목록 검색
     * @param request : 전달된 Request
     * @return Mono<ServerResponse> : 날짜별 가계부 정보
     */
    public Mono<ServerResponse> accountBookSearch(ServerRequest request) {
        log.info("---------- 핸들러");
        Mono<AccountBookSearchResponse> response = accountBookApplicationService.accountBookSearch(request)
                .subscribeOn(Schedulers.boundedElastic()).log();

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, AccountBookSearchResponse.class);
    }

    /**
     * 월 별 통계
     * @param request : 회원 정보 및 조회할 월
     * @return Mono<ServerResponse> : AccountBookStatisticResponse
     */
    public Mono<ServerResponse> accountBookStatistic(ServerRequest request) {

        Mono<AccountBookStatisticResponse> response = accountBookApplicationService.accountBookStatistic(request)
            .subscribeOn(Schedulers.boundedElastic());

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, AccountBookStatisticResponse.class);
    }
}
