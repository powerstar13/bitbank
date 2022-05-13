package click.bitbank.api.infrastructure.config;

import click.bitbank.api.application.response.CardPopularListResponse;
import click.bitbank.api.presentation.card.CardHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
            path = "/card/popular-list",
            produces = { MediaType.APPLICATION_JSON_VALUE },
            headers = { HttpHeaders.AUTHORIZATION },
            beanClass = CardHandler.class,
            method = RequestMethod.GET,
            beanMethod = "cardPopularList",
            operation = @Operation(
                description = "인기 카드 목록 조회 API",
                operationId = "cardPopularList",
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = CardPopularListResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        )
    })
    @Bean
    public RouterFunction<ServerResponse> cardRouterBuilder(CardHandler cardHandler) {

        return RouterFunctions.route()
            .path("/card", cardBuilder ->
                cardBuilder.nest(accept(MediaType.APPLICATION_JSON), builder ->
                    builder
                        .GET("/popular-list", cardHandler::cardPopularList) // 인기 카드 목록 조회
                )
            )
            .build();
    }

}
