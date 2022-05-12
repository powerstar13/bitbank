package click.bitbank.api.domain.accountBook.repository;

import click.bitbank.api.domain.accountBook.model.Expenditure;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ExpenditureRepository extends ReactiveCrudRepository<Expenditure, Integer> {
}
