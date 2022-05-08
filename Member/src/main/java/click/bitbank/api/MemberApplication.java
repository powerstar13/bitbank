package click.bitbank.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(exclude = {R2dbcDataAutoConfiguration.class, R2dbcAutoConfiguration.class})
@EnableDiscoveryClient // Eureka Client 역할을 수행한다. YAML에 명시한 유레카 서버에 해당 애플리케이션을 등록한다.
public class MemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}

}
