package click.bitbank.api.domain.model.member;

import click.bitbank.api.domain.repository.MemberRepository;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.infrastructure.exception.status.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberFindSpecification {
    
    private final MemberRepository memberRepository;
    
    /**
     * 회원 정보 검증
     * @param memberId : 회원 고유번호
     * @return Mono<Member> : 조회된 회원 정보
     */
    public Mono<Member> membmerExistVerify(int memberId) {
        
        return memberRepository.findById(memberId)
            .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundMember.getMessage())));
    }
}
