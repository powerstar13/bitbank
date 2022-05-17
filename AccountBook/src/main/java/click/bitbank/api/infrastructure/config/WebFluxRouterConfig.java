package click.bitbank.api.infrastructure.config;

import click.bitbank.api.application.response.AccountBookSearchResponse;
import click.bitbank.api.presentation.accountBook.AccountBookHandler;
import click.bitbank.api.presentation.accountBook.request.AccountBookSearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@EnableWebFlux // WebFlux 설정 활성화
public class WebFluxRouterConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .maxAge(3600);
    }


    @RouterOperations({
        @RouterOperation(
            path = "/account-book/search",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            headers = {HttpHeaders.AUTHORIZATION},
            beanClass = AccountBookHandler.class,
            method = RequestMethod.GET,
            beanMethod = "accountBookSearch",
            operation = @Operation(
                description = "가계부 목록 검색 API",
                operationId = "accountBookSearch",
                requestBody = @RequestBody(
                        content = @Content(
                                schema = @Schema(
                                        implementation = AccountBookSearchRequest.class,
                                        required = true
                                )
                        )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = AccountBookSearchResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        )
    })
    @Bean
    public RouterFunction<ServerResponse> accountBookRouterBuilder(AccountBookHandler accountBookHandler) {
        return RouterFunctions.route()
                .path("/account-book", builder -> builder
                        .nest(accept(MediaType.APPLICATION_JSON), acceptBuilder ->
                                acceptBuilder
//                                        .POST("/", accountBookHandler::accountBookWrite) // 가계부 작성
                                        .POST("/search", accountBookHandler::accountBookSearch) // 가계부 목록 검색
                        )
                ).build();
    }

}
