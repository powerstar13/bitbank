package click.bitbank.api.infrastructure.config;

import click.bitbank.api.application.response.AlarmCountResponse;
import click.bitbank.api.application.response.AlarmListResponse;
import click.bitbank.api.application.response.MemberLoginResponse;
import click.bitbank.api.application.response.MemberSignupResponse;
import click.bitbank.api.presentation.member.MemberHandler;
import click.bitbank.api.presentation.member.request.MemberIdRequest;
import click.bitbank.api.presentation.member.request.MemberLoginRequest;
import click.bitbank.api.presentation.member.request.MemberSignupRequest;
import click.bitbank.api.presentation.member.request.SocialLoginRequest;
import click.bitbank.api.presentation.shared.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
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
            path = "/auth/signup",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            beanClass = MemberHandler.class,
            method = RequestMethod.POST,
            beanMethod = "memberRegistration",
            operation = @Operation(
                description = "일반 회원 가입 API",
                operationId = "signup",
                requestBody = @RequestBody(
                    content = @Content(
                        schema = @Schema(
                            implementation = MemberSignupRequest.class,
                            required = true
                        )
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "201",
                        content = @Content(
                            schema = @Schema(
                                implementation = MemberSignupResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/auth/login/social",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            beanClass = MemberHandler.class,
            method = RequestMethod.POST,
            beanMethod = "loginSocial",
            operation = @Operation(
                description = "소셜 로그인 API",
                operationId = "loginSocial",
                requestBody = @RequestBody(
                    content = @Content(
                        schema = @Schema(
                            implementation = SocialLoginRequest.class,
                            required = true
                        )
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = MemberLoginResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/auth/login",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            beanClass = MemberHandler.class,
            method = RequestMethod.POST,
            beanMethod = "login",
            operation = @Operation(
                description = "로그인 API",
                operationId = "login",
                requestBody = @RequestBody(
                    content = @Content(
                        schema = @Schema(
                            implementation = MemberLoginRequest.class,
                            required = true
                        )
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = MemberLoginResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/member/alarm-count",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            headers = { HttpHeaders.AUTHORIZATION },
            beanClass = MemberHandler.class,
            method = RequestMethod.GET,
            beanMethod = "alarm-count",
            operation = @Operation(
                description = "읽지 않은 알림 갯수 API",
                operationId = "alarm-count",
                parameters = {
                    @Parameter(
                        in = ParameterIn.QUERY,
                        name = "memberId",
                        description = "회원 고유번호",
                        required = true,
                        example = "1"
                    )
                },
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = AlarmCountResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/member/alarm-list",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            headers = { HttpHeaders.AUTHORIZATION },
            beanClass = MemberHandler.class,
            method = RequestMethod.GET,
            beanMethod = "alarm-list",
            operation = @Operation(
                description = "읽지 않은 알림 목록 API",
                operationId = "alarm-list",
                parameters = {
                    @Parameter(
                        in = ParameterIn.QUERY,
                        name = "memberId",
                        description = "회원 고유번호",
                        required = true,
                        example = "1"
                    )
                },
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = AlarmListResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/member/logout",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            headers = { HttpHeaders.AUTHORIZATION },
            beanClass = MemberHandler.class,
            method = RequestMethod.POST,
            beanMethod = "logout",
            operation = @Operation(
                description = "로그아웃 API",
                operationId = "logout",
                requestBody = @RequestBody(
                    content = @Content(
                        schema = @Schema(
                            implementation = MemberIdRequest.class,
                            required = true
                        )
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = SuccessResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/member/modification",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            headers = { HttpHeaders.AUTHORIZATION },
            beanClass = MemberHandler.class,
            method = RequestMethod.POST,
            beanMethod = "modification",
            operation = @Operation(
                description = "회원 정보 수정 API",
                operationId = "modification",
                requestBody = @RequestBody(
                    content = @Content(
                        schema = @Schema(
                            implementation = MemberIdRequest.class,
                            required = true
                        )
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = SuccessResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/member/delete",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            headers = { HttpHeaders.AUTHORIZATION },
            beanClass = MemberHandler.class,
            method = RequestMethod.DELETE,
            beanMethod = "delete",
            operation = @Operation(
                description = "회원 탈퇴 API",
                operationId = "delete",
                requestBody = @RequestBody(
                    content = @Content(
                        schema = @Schema(
                            implementation = MemberIdRequest.class,
                            required = true
                        )
                    )
                ),
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = SuccessResponse.class,
                                required = true
                            )
                        )
                    )
                }
            )
        ),
        @RouterOperation(
            path = "/member/exist-verify",
            consumes = { MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_JSON_VALUE },
            beanClass = MemberHandler.class,
            method = RequestMethod.GET,
            beanMethod = "existVerify",
            operation = @Operation(
                description = "회원 검증 API",
                operationId = "existVerify",
                parameters = {
                    @Parameter(
                        in = ParameterIn.QUERY,
                        name = "memberId",
                        description = "회원 고유번호",
                        required = true,
                        example = "1"
                    )
                },
                responses = {
                    @ApiResponse(
                        responseCode = "200",
                        content = @Content(
                            schema = @Schema(
                                implementation = SuccessResponse.class,
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
            .path("/auth", memberBuilder ->
                memberBuilder.nest(accept(MediaType.APPLICATION_JSON), builder ->
                    builder
                        .POST("/signup", memberHandler::signup) // 학생 회원 가입
                        .POST("/login/social", memberHandler::socialLogin) // 소셜 로그인
                        .POST("/login", memberHandler::login) // 일반 회원 로그인
                )
            )
            .path("/member", memberBuilder ->
                memberBuilder
                    .GET("/alarm-count", memberHandler::alarmCount) // 읽지 않은 알림 갯수 조회
                    .GET("/alarm-list", memberHandler::alarmList) // 읽지 않은 알림 목록 조회
                    .GET("/exist-verify", memberHandler::existVerify) // 회원 검증
            )
            .path("/member", memberBuilder ->
                memberBuilder.nest(accept(MediaType.APPLICATION_JSON), builder ->
                    builder
                        .POST("/logout", memberHandler::logout) // 로그아웃
                        .PUT("/modification", memberHandler::modification) // 회원 정보 수정
                        .DELETE("/delete", memberHandler::delete) // 회원 탈퇴
                )
            )
            .build();
    }
}
