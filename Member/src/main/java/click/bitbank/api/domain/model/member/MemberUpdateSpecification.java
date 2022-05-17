package click.bitbank.api.domain.model.member;

import click.bitbank.api.domain.repository.MemberRepository;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.infrastructure.exception.status.NotFoundDataException;
import click.bitbank.api.presentation.member.request.MemberModificationRequest;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MemberUpdateSpecification {

    private final MemberRepository memberRepository;

    /**
     * 회원 검증 및 정보 수정 처리
     * @param request : 수정할 회원 정보
     * @return Mono<SuccessResponse> : 성공 여부
     */
    public Mono<SuccessResponse> memberExistCheckAndModification(MemberModificationRequest request) {

        Mono<Member> memberMono = memberRepository.findById(request.getMemberId());

        return memberMono
            .hasElement()
            .flatMap(aBoolean -> {
                if (!aBoolean) return Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundMember.getMessage()));

                return memberMono.flatMap(member -> {
                        member.modify(request.getMemberName(), request.getMemberPassword()); // 회원 정보 수정

                        return memberRepository.save(member)
                            .flatMap(savedMember -> Mono.just(new SuccessResponse()));
                    });
            }
        );
    }
}
