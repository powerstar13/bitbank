package click.bitbank.api.application.accountBook;

import click.bitbank.api.application.response.AccountBookSearchResponse;
import click.bitbank.api.application.response.AccountBookWriteResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface AccountBookApplicationService {

    Mono<AccountBookSearchResponse> accountBookSearch(ServerRequest serverRequest);   // 가계부 목록 검색

    Mono<AccountBookWriteResponse> accountBookWrite(ServerRequest serverRequest); // 가계부 작성
}
