package click.bitbank.api.domain.model.accountBook;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBookRepository extends ReactiveCrudRepository<AccountBook, Integer> {
}
