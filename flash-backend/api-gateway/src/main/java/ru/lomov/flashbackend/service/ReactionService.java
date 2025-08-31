package ru.lomov.flashbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ReactionService {

    private final WebClient webClient;

    @Autowired
    public ReactionService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://reaction-service").build();
    }

    public Mono<String> addReaction(String userId, String postId, String reactionType) {
        return webClient.post()
                .uri("/reaction/{userId}/{postId}/{reactionType}", userId, postId, reactionType)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> removeReaction(String userId, String postId) {
        return webClient.delete()
                .uri("/reaction/{userId}/{postId}", userId, postId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getReactionsForPost(String postId) {
        return webClient.get()
                .uri("/reaction/post/{postId}", postId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getUserReactions(String userId) {
        return webClient.get()
                .uri("/reaction/user/{userId}", userId)
                .retrieve()
                .bodyToMono(String.class);
    }
}
