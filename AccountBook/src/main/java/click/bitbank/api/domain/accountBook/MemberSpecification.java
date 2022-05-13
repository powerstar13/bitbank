package click.bitbank.api.domain.accountBook;

import click.bitbank.api.domain.accountBook.model.Member;
import click.bitbank.api.domain.accountBook.repository.MemberRepository;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.infrastructure.exception.status.NotFoundDataException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberSpecification {

    private final MemberRepository memberRepository;

    public Mono<Member> memberExistenceCheck(int memberId) {
        log.info("-----------------  여긴 멤버 존재 확인 시작");

        return memberRepository.findByMemberId(memberId)
                .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundLoginMember.getMessage()))).log();
    }
}
