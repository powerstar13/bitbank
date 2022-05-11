package click.bitbank.api.domain.model.member;

import click.bitbank.api.infrastructure.exception.status.AlreadyDataException;
import click.bitbank.api.infrastructure.exception.status.RegistrationFailException;
import click.bitbank.api.presentation.member.request.MemberRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import click.bitbank.api.application.response.MemberRegistrationResponse;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;

@Component
@RequiredArgsConstructor
public class MemberSaveSpecification {

    private final MemberRepository memberRepository;
    private final MemberFactory memberFactory;

    /**
     * 회원 중복 검사 및 계정 생성
     * @param request : 저장할 회원 정보
     * @param memberType : 저장할 회원 유형
     * @return Mono<MemberRegistrationResponse> : 저장된 회원 정보
     */
    public Mono<MemberRegistrationResponse> memberExistCheckAndRegistration(MemberRegistrationRequest request, MemberType memberType) {

        return memberRepository.findByMemberNameAndMemberType(request.getMemberName(), memberType)
            .hasElement()
            .flatMap(alreadyMember -> {

                if (alreadyMember) return Mono.error(new AlreadyDataException(ExceptionMessage.AlreadyDataMember.getMessage()));

                return this.memberRegistration(request, memberType)
                    .flatMap(savedMember -> Mono.just(
                        MemberRegistrationResponse.builder()
                            .memberId(savedMember.getMemberId())
                            .build()
                    ));
            }
        );
    }

    /**
     * 회원 계정 생성
     * @param request : 저장할 회원 정보
     * @param memberType : 저장할 회원 유형
     * @return Mono<Member> : 저장된 회원 정보
     */
    private Mono<Member> memberRegistration(MemberRegistrationRequest request, MemberType memberType) {

        return memberRepository.save(
            memberFactory.memberBuilder(
                request.getMemberName(),
                request.getMemberPassword(),
                memberType
            )
        ).switchIfEmpty(Mono.error(new RegistrationFailException(ExceptionMessage.SaveFailMember.getMessage())));
    }
}
