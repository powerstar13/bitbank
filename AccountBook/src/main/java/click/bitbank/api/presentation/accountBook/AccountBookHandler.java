package click.bitbank.api.presentation.accountBook;

import click.bitbank.api.application.accountBook.AccountBookApplicationService;
import click.bitbank.api.application.response.AccountBookSearchResponse;
import click.bitbank.api.application.response.DTO.AccountBookSearchByDailyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountBookHandler {

    private final AccountBookApplicationService accountBookApplicationService;

    public Mono<ServerResponse> accountBookSearch(ServerRequest request) {
        log.info("-----------------  여긴 핸들러 시작");
        Mono<List<AccountBookSearchByDailyDTO>> response = accountBookApplicationService.accountBookSearch(request)
                .subscribeOn(Schedulers.boundedElastic()).log();
        log.info("-----------------  여긴 핸들러 종료 지점");

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response, AccountBookSearchResponse.class);
    }
}
