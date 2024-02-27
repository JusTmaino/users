package fr.afklm.users.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WiremockConfiguration {

    @Value("${wiremock.port}")
    private final Integer port;
    private WireMockServer wireMockServer;

    @PostConstruct
    private void postConstruct() {
        wireMockServer = new WireMockServer(
                WireMockConfiguration
                        .options()
                        .port(this.port)
                        .containerThreads(8)
                        .jettyAcceptors(4)
                        .jettyAcceptQueueSize(100)
                        .jettyHeaderBufferSize(16834)
                        .asynchronousResponseEnabled(true)
                        .asynchronousResponseThreads(10)
        );

        try {
            wireMockServer.start();
        } catch (Exception e) {
            log.error("Could not start wiremock server on port {}, exception : {}", this.port, e.getMessage());
            return;
        }
        configureFor(this.port);
        log.info("Wiremock server started on port {}", this.port);
    }

    @PreDestroy
    private void preDestroy() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}
