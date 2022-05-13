package click.bitbank.api.domain.model.card;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface CardRepository extends ReactiveCrudRepository<Card, Integer> {

    Flux<Card> findTop10ByOrderByCardRankingAsc();
}
