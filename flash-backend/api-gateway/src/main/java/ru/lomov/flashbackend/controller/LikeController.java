package ru.lomov.flashbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.lomov.flashbackend.dto.ApiResponse;
import ru.lomov.flashbackend.service.LikeService;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{userId}/{postId}")
    public Mono<ResponseEntity<ApiResponse<String>>> likePost(
            @PathVariable("userId") String userId,
            @PathVariable("postId") String postId) {
        return likeService.likePost(userId, postId)
                .map(result -> ResponseEntity.ok(ApiResponse.success("Post liked successfully", result)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }

    @DeleteMapping("/{userId}/{postId}")
    public Mono<ResponseEntity<ApiResponse<String>>> unlikePost(
            @PathVariable("userId") String userId,
            @PathVariable("postId") String postId) {
        return likeService.unlikePost(userId, postId)
                .map(result -> ResponseEntity.ok(ApiResponse.success("Post unliked successfully", result)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }

    @GetMapping("/post/{postId}")
    public Mono<ResponseEntity<ApiResponse<String>>> getLikesForPost(@PathVariable("postId") String postId) {
        return likeService.getLikesForPost(postId)
                .map(likes -> ResponseEntity.ok(ApiResponse.success(likes)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }

    @GetMapping("/user/{userId}")
    public Mono<ResponseEntity<ApiResponse<String>>> getUserLikes(@PathVariable("userId") String userId) {
        return likeService.getUserLikes(userId)
                .map(likes -> ResponseEntity.ok(ApiResponse.success(likes)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }
}
