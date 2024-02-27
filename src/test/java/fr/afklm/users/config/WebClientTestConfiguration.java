package fr.afklm.users.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class WebClientTestConfiguration {

    @Bean
    public WebClient getWebClient(final WebClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8080")
                // More configurations and customizations
                .build();
    }
}
