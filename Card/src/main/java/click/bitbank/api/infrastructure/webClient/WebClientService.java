package click.bitbank.api.infrastructure.webClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientService {

    private WebClient memberClient;

    @Value("${msa.client.url.member}")
    private String memberUrl;

    public WebClientService(WebClient.Builder builder) {
        this.memberClient = builder.baseUrl(memberUrl).build();
    }

}
