package click.bitbank.api.domain.accountBook.repository;

import click.bitbank.api.domain.accountBook.model.Member;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MemberRepository extends ReactiveCrudRepository<Member, Integer> {
    Mono<Member> findByMemberId(int memberId);
}
