package click.bitbank.api.domain.accountBook;

import click.bitbank.api.domain.accountBook.model.Member;
import click.bitbank.api.domain.accountBook.repository.MemberRepository;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.infrastructure.exception.status.NotFoundDataException;
import click.bitbank.api.infrastructure.webClient.WebClientService;
import click.bitbank.api.presentation.shared.response.CommonResponse;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberSpecification {

    private final MemberRepository memberRepository;
    private final WebClientService webClientService;

    /**
     * 회원 존재 여부 확인
     * @param memberId : 회원 고유 번호
     * @return Mono<Member> : 회원 고유 번호
     */
    public Mono<Member> memberExistenceCheck(int memberId) {
        return memberRepository.findByMemberId(memberId)
                .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundLoginMember.getMessage()))).log();
    }

    /**
     * 회원 검증
     * @param memberId : 회원 고유번호
     * @return Mono<SuccessResponse> : 검증 성공
     */
    public Mono<CommonResponse> memberExistVerify(int memberId) {
        return webClientService.callMemberVerify(memberId);
    }
}
