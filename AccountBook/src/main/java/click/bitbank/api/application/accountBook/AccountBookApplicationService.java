package click.bitbank.api.application.accountBook;

import click.bitbank.api.domain.accountBook.model.Income;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountBookApplicationService {

    Mono<List<Income>> accountBookSearch(ServerRequest serverRequest);   // 가계부 목록 검색
}
