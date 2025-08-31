package ru.lomov.flashbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FollowService {

    private final WebClient webClient;

    @Autowired
    public FollowService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://follow-service").build();
    }

    public Mono<String> followUser(String followerId, String followingId) {
        return webClient.post()
                .uri("/follow/{followerId}/{followingId}", followerId, followingId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> unfollowUser(String followerId, String followingId) {
        return webClient.delete()
                .uri("/follow/{followerId}/{followingId}", followerId, followingId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getFollowers(String userId) {
        return webClient.get()
                .uri("/follow/followers/{userId}", userId)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> getFollowing(String userId) {
        return webClient.get()
                .uri("/follow/following/{userId}", userId)
                .retrieve()
                .bodyToMono(String.class);
    }
}
