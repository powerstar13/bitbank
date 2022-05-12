package click.bitbank.api.presentation.accountBook;

import click.bitbank.api.application.accountBook.AccountBookApplicationService;
import click.bitbank.api.application.response.AccountBookSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class AccountBookHandler {

    private AccountBookApplicationService accountBookApplicationService;

    public Mono<ServerResponse> accountBookSearch(ServerRequest request) {

        Mono<AccountBookSearchResponse> response = accountBookApplicationService.accountBookSearch(request);

        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("test", ServerResponse.class);
    }
}
