package click.bitbank.api.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
    info = @Info(
        title = "금융 매칭 Domain Server",
        version = "v1.0.0",
        description = "금융 매칭 서비스 API 문서"
    )
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi cardApi() {

        return GroupedOpenApi.builder()
            .group("card")
            .pathsToMatch("/card/**")
            .build();
    }
}
