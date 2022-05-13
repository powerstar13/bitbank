package click.bitbank.api.application.accountBook;

import click.bitbank.api.domain.accountBook.MemberSpecification;
import click.bitbank.api.domain.accountBook.model.Member;
import click.bitbank.api.infrastructure.exception.status.BadRequestException;
import click.bitbank.api.infrastructure.exception.status.ExceptionMessage;
import click.bitbank.api.infrastructure.exception.status.NotFoundDataException;
import click.bitbank.api.presentation.accountBook.request.AccountBookSearchRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountBookApplicationServiceImpl implements AccountBookApplicationService {

    private final MemberSpecification memberSpecification;

    /**
     * 가계부 목록 검색
     *
     * @param serverRequest : 전달된 Request
     * @return Mono<AccountBookSearchResponse> : 저장된 가계부 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = true)
    public Mono<Member> accountBookSearch(ServerRequest serverRequest) {
        log.info("-----------------  여긴 서비스 시작");
        return serverRequest.bodyToMono(AccountBookSearchRequest.class).flatMap(
                request -> {
                    request.verify();
                    return memberSpecification.memberExistenceCheck(request.getMemberId()).log();
                }).switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())));
    }
}
