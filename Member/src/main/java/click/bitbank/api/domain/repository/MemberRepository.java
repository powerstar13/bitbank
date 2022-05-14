package click.bitbank.api.domain.repository;

import click.bitbank.api.domain.model.member.Member;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Integer> {
    // README : 삭제된 회원을 조회하지 않도록 모든 SQL문에 delDate IS NULL 추가 필요

    @Query(value = "SELECT * FROM member WHERE delDate IS NULL AND memberId = :memberId")
    Mono<Member> findById(@Param("memberId") int memberId);

    Mono<Member> findByMemberLoginIdAndDelDateIsNull(String memberLoginId);

    Mono<Member> findByMemberLoginIdAndMemberPasswordAndDelDateIsNull(String memberLoginId, String memberPassword);
}
