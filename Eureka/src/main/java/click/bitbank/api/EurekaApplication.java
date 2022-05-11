package click.bitbank.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@Slf4j
@SpringBootApplication
@EnableEurekaServer // 유레카 서버의 역할을 수행한다.
public class EurekaApplication {

    private static String active; // active profile

    // static 변수에는 @Value 어노테이션이 동작하지 않는다. setter 메서드를 이용하여 값이 할당되도록 하면 된다.
    @Value("${spring.profiles.active}")
    private void setActive(String value) {
        active = value;
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);

        log.warn(String.format(" ::: [bitbank Application Run] SUCCESS * ACTIVE PROFILE --> %s ::: ", active));
    }

}
