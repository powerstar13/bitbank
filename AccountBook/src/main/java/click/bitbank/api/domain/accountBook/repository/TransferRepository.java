package click.bitbank.api.domain.accountBook.repository;

import click.bitbank.api.domain.accountBook.model.Transfer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TransferRepository extends ReactiveCrudRepository<Transfer, Integer> {

}
