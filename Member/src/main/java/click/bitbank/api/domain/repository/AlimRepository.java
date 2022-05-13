package click.bitbank.api.domain.repository;

import click.bitbank.api.domain.model.alim.Alim;
import click.bitbank.api.domain.model.alim.AlimCheck;
import click.bitbank.api.domain.model.member.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AlimRepository extends ReactiveCrudRepository<Alim, Integer> {

    Mono<Long> countByMemberIdAndAlimCheck(int memberId, AlimCheck alimCheck);
}
