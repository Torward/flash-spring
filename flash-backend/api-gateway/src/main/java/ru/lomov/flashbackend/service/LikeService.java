package ru.lomov.flashbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LikeService {

    private final WebClient webClient;

    @Autowired
    public LikeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://likes-service").build();
    }

    public Mono<String> likePost(String userId, String postId) {
        return webClient.post()
                .uri("/likes/{userId}/{postId}", userId, postId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> unlikePost(String userId, String postId) {
        return webClient.delete()
                .uri("/likes/{userId}/{postId}", userId, postId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getLikesForPost(String postId) {
        return webClient.get()
                .uri("/likes/post/{postId}", postId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getUserLikes(String userId) {
        return webClient.get()
                .uri("/likes/user/{userId}", userId)
                .retrieve()
                .bodyToMono(String.class);
    }
}
