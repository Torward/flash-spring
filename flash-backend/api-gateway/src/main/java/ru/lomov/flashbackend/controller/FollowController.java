package ru.lomov.flashbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.lomov.flashbackend.dto.ApiResponse;
import ru.lomov.flashbackend.service.FollowService;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/{followerId}/{followingId}")
    public Mono<ResponseEntity<ApiResponse<String>>> followUser(
            @PathVariable("followerId") String followerId,
            @PathVariable("followingId") String followingId) {
        return followService.followUser(followerId, followingId)
                .map(result -> ResponseEntity.ok(ApiResponse.success("User followed successfully", result)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }

    @DeleteMapping("/{followerId}/{followingId}")
    public Mono<ResponseEntity<ApiResponse<String>>> unfollowUser(
            @PathVariable("followerId") String followerId,
            @PathVariable("followingId") String followingId) {
        return followService.unfollowUser(followerId, followingId)
                .map(result -> ResponseEntity.ok(ApiResponse.success("User unfollowed successfully", result)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }

    @GetMapping("/followers/{userId}")
    public Mono<ResponseEntity<ApiResponse<String>>> getFollowers(@PathVariable("userId") String userId) {
        return followService.getFollowers(userId)
                .map(followers -> ResponseEntity.ok(ApiResponse.success(followers)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }

    @GetMapping("/following/{userId}")
    public Mono<ResponseEntity<ApiResponse<String>>> getFollowing(@PathVariable("userId") String userId) {
        return followService.getFollowing(userId)
                .map(following -> ResponseEntity.ok(ApiResponse.success(following)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }
}
