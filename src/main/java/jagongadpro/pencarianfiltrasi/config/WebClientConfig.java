package jagongadpro.pencarianfiltrasi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.logging.Logger;

@Configuration
public class WebClientConfig {

    private static final Logger logger = Logger.getLogger(WebClientConfig.class.getName());

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .filter((request, next) -> {
                    logger.info("Request: " + request.url());
                    return next.exchange(request);
                });
    }
}
