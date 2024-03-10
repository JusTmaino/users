package tech.byrsa.users.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {

    @Value("${users.remote-service.base-url}")
    private final String baseUrl;

    @Bean("UserWebClient")
    public WebClient userWebClient(final WebClient.Builder builder) {
        return builder
                .baseUrl(baseUrl)
                // More configurations and customizations
                .build();
    }
}
