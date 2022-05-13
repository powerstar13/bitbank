package click.bitbank.api.application.accountBook;

import click.bitbank.api.domain.accountBook.model.Member;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface AccountBookApplicationService {

    Mono<Member> accountBookSearch(ServerRequest serverRequest);   // 가계부 목록 검색
}
