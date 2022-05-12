package click.bitbank.api.infrastructure.config;

import click.bitbank.api.application.response.MemberInfoResponse;
import click.bitbank.api.application.response.MemberRegistrationResponse;
import click.bitbank.api.presentation.accountBook.AccountBookHandler;
import click.bitbank.api.presentation.member.MemberHandler;
import click.bitbank.api.presentation.member.request.MemberRegistrationRequest;
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
            path = "/member/admin/teacherRegistration",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            headers = { HttpHeaders.AUTHORIZATION },
            beanClass = MemberHandler.class,
            method = RequestMethod.POST,
            beanMethod = "memberRegistration",
            operation = @Operation(
                description = "강사 등록 API",
                operationId = "teacherRegistration",
                requestBody = @RequestBody(
                    content = @Content(
                        schema = @Schema(
                            implementation = MemberRegistrationRequest.class,
                            required = true
                        )
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "201",
                        content = @Content(
                            schema = @Schema(
                                implementation = MemberRegistrationResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/member/studentRegistration",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            beanClass = MemberHandler.class,
            method = RequestMethod.POST,
            beanMethod = "memberRegistration",
            operation = @Operation(
                description = "학생 회원 가입 API",
                operationId = "studentRegistration",
                requestBody = @RequestBody(
                    content = @Content(
                        schema = @Schema(
                            implementation = MemberRegistrationRequest.class,
                            required = true
                        )
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "201",
                        content = @Content(
                            schema = @Schema(
                                implementation = MemberRegistrationResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        )
    })
    @Bean
    public RouterFunction<ServerResponse> memberRouterBuilder(MemberHandler memberHandler) {

        return RouterFunctions.route()
            .path("/member", memberBuilder ->
                memberBuilder.nest(accept(MediaType.APPLICATION_JSON), builder ->
                    builder
                        .POST("/admin/teacherRegistration", memberHandler::memberRegistration) // 강사 등록 (관리자만)
                        .POST("/studentRegistration", memberHandler::memberRegistration) // 학생 회원 가입
                )
            )
            .build();
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
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = MemberInfoResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        )
    })
    @Bean
    public RouterFunction<ServerResponse> accountBookRouterGETBuilder(AccountBookHandler accountBookHandler) {
        return RouterFunctions.route()
            .path("/account-book", builder -> builder
                .GET("/search", accountBookHandler::accountBookSearch)
            ).build();
    }

}
