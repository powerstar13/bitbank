package click.bitbank.api.domain.model.member;

import click.bitbank.api.domain.repository.MemberRepository;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.infrastructure.exception.status.NotFoundDataException;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberDeleteSpecification {

    private final MemberRepository memberRepository;

    /**
     * 회원 탈퇴 처리
     * @param memberId : 회원 고유번호
     * @return Mono<SuccessResponse> : 성공 여부
     */
    public Mono<SuccessResponse> memberExistCheckAndDelete(int memberId) {

        return memberRepository.findById(memberId)
            .flatMap(member -> {
                member.delete();
                return memberRepository.save(member);
            })
            .flatMap(member -> Mono.just(new SuccessResponse()))
            .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundMember.getMessage())));
    }
}
