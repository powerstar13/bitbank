package click.bitbank.api.domain.accountBook.repository;

import click.bitbank.api.domain.accountBook.model.Income;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IncomeRepository extends ReactiveCrudRepository<Income, Integer> {

}
