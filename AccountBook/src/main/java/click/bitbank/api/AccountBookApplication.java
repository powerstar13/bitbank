package click.bitbank.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@Slf4j
@SpringBootApplication(exclude = {R2dbcDataAutoConfiguration.class, R2dbcAutoConfiguration.class})
@EnableDiscoveryClient // Eureka Client 역할을 수행한다. YAML에 명시한 유레카 서버에 해당 애플리케이션을 등록한다.
public class AccountBookApplication {

	private static String active; // active profile

	// static 변수에는 @Value 어노테이션이 동작하지 않는다. setter 메서드를 이용하여 값이 할당되도록 하면 된다.
	@Value("${spring.profiles.active}")
	private void setActive(String value) {
		active = value;
	}

	public static void main(String[] args) {
		SpringApplication.run(AccountBookApplication.class, args);

		log.warn(String.format(" ::: [bitbank Application Run] SUCCESS * ACTIVE PROFILE --> %s ::: ", active));
	}

}
