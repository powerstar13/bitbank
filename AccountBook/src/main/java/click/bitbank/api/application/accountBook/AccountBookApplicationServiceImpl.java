package click.bitbank.api.application.accountBook;

import click.bitbank.api.application.response.AccountBookSearchResponse;
import click.bitbank.api.presentation.accountBook.request.AccountBookSearchRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public class AccountBookApplicationServiceImpl implements AccountBookApplicationService {

    /**
     * 가계부 목록 검색
     * @param serverRequest : 전달된 Request
     * @return Mono<AccountBookSearchResponse> : 저장된 가계부 정보
     */
    @Override
    public Mono<AccountBookSearchResponse> accountBookSearch(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(AccountBookSearchRequest.class).flatMap(
                request -> {
                    request.verify(); // Request 유효성 검사
                    return null;

                }
        );
    }
}
