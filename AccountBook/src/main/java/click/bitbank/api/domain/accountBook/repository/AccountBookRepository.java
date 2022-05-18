package click.bitbank.api.domain.accountBook.repository;

import click.bitbank.api.domain.accountBook.model.AccountBook;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AccountBookRepository extends ReactiveCrudRepository<AccountBook, Integer> {
    Mono<AccountBook> findByAccountBookId(int accountBookId);
}
