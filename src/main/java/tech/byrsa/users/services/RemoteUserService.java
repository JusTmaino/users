package tech.byrsa.users.services;

import tech.byrsa.users.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RemoteUserService {

    private final WebClient webClient;

    public User getUserInfo(long userId) {
        return webClient.get()
                .uri("/api/user/" + userId)
                .retrieve().bodyToMono(User.class)
                .doOnError(throwable -> log.error("Remote service error", throwable))
                .onErrorResume(WebClientResponseException.class,
                        ex -> Mono.empty())
                .block();
    }
}
