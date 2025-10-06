package ru.lomov.flash.reaction.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.reaction.dto.ReactionResponse;
import ru.lomov.flash.reaction.services.ReactionService;

import java.util.List;

@RestController
@RequestMapping("/reaction")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping("/{postId}/{userId}/{reactionType}")
    public ResponseEntity<ReactionResponse> addReaction(
            @PathVariable String postId,
            @PathVariable String userId,
            @PathVariable String reactionType) {
        ReactionResponse response = reactionService.addReaction(postId, userId, reactionType);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{postId}/{userId}/{reactionType}")
    public ResponseEntity<ReactionResponse> updateReaction(
            @PathVariable String postId,
            @PathVariable String userId,
            @PathVariable String reactionType) {
        ReactionResponse response = reactionService.updateReaction(postId, userId, reactionType);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{postId}/{userId}")
    public ResponseEntity<ReactionResponse> removeReaction(
            @PathVariable String postId,
            @PathVariable String userId) {
        ReactionResponse response = reactionService.removeReaction(postId, userId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{postId}/{userId}")
    public ResponseEntity<ReactionResponse> getReaction(
            @PathVariable String postId,
            @PathVariable String userId) {
        ReactionResponse response = reactionService.getReaction(postId, userId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<ReactionResponse>> getPostReactions(
            @PathVariable String postId) {
        List<ReactionResponse> reactions = reactionService.getPostReactions(postId);
        return ResponseEntity.ok(reactions);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReactionResponse>> getUserReactions(
            @PathVariable String userId) {
        List<ReactionResponse> reactions = reactionService.getUserReactions(userId);
        return ResponseEntity.ok(reactions);
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> getPostReactionsCount(
            @PathVariable String postId) {
        long count = reactionService.getPostReactionsCount(postId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getUserReactionsCount(
            @PathVariable String userId) {
        long count = reactionService.getUserReactionsCount(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{postId}/{userId}/hasReacted")
    public ResponseEntity<Boolean> hasUserReacted(
            @PathVariable String postId,
            @PathVariable String userId) {
        boolean hasReacted = reactionService.hasUserReacted(postId, userId);
        return ResponseEntity.ok(hasReacted);
    }

    @GetMapping("/{postId}/{userId}/type")
    public ResponseEntity<String> getUserReactionType(
            @PathVariable String postId,
            @PathVariable String userId) {
        String reactionType = reactionService.getUserReactionType(postId, userId);
        return reactionType != null ? ResponseEntity.ok(reactionType) : ResponseEntity.notFound().build();
    }


}
