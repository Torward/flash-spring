package ru.lomov.flashbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.lomov.flashbackend.dto.ApiResponse;
import ru.lomov.flashbackend.service.ReactionService;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    private final ReactionService reactionService;

    @Autowired
    public ReactionController(ReactionService reactionService) {
        this.reactionService = reactionService;
    }

    @PostMapping("/{userId}/{postId}/{reactionType}")
    public Mono<ResponseEntity<ApiResponse<String>>> addReaction(
            @PathVariable("userId") String userId,
            @PathVariable("postId") String postId,
            @PathVariable("reactionType") String reactionType) {
        return reactionService.addReaction(userId, postId, reactionType)
                .map(result -> ResponseEntity.ok(ApiResponse.success("Reaction added successfully", result)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }

    @DeleteMapping("/{userId}/{postId}")
    public Mono<ResponseEntity<ApiResponse<String>>> removeReaction(
            @PathVariable("userId") String userId,
            @PathVariable("postId") String postId) {
        return reactionService.removeReaction(userId, postId)
                .map(result -> ResponseEntity.ok(ApiResponse.success("Reaction removed successfully", result)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }

    @GetMapping("/post/{postId}")
    public Mono<ResponseEntity<ApiResponse<String>>> getReactionsForPost(@PathVariable("postId") String postId) {
        return reactionService.getReactionsForPost(postId)
                .map(reactions -> ResponseEntity.ok(ApiResponse.success(reactions)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }

    @GetMapping("/user/{userId}")
    public Mono<ResponseEntity<ApiResponse<String>>> getUserReactions(@PathVariable("userId") String userId) {
        return reactionService.getUserReactions(userId)
                .map(reactions -> ResponseEntity.ok(ApiResponse.success(reactions)))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()))));
    }
}
