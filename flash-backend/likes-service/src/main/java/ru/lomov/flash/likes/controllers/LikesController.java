package ru.lomov.flash.likes.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.likes.dto.LikeResponse;
import ru.lomov.flash.likes.services.LikeService;

import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {

    private final LikeService likeService;

    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<LikeResponse> likePost(
            @PathVariable String postId,
            @PathVariable String userId) {
        LikeResponse response = likeService.likePost(postId, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}/{userId}")
    public ResponseEntity<LikeResponse> unlikePost(
            @PathVariable String postId,
            @PathVariable String userId) {
        LikeResponse response = likeService.unlikePost(postId, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}/count")
    public ResponseEntity<Long> getLikesCount(@PathVariable String postId) {
        long count = likeService.getLikesCount(postId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{postId}/users")
    public ResponseEntity<List<LikeResponse>> getPostLikes(@PathVariable String postId) {
        List<LikeResponse> likes = likeService.getPostLikes(postId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<LikeResponse>> getUserLikes(@PathVariable String userId) {
        List<LikeResponse> likes = likeService.getUserLikes(userId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/{postId}/{userId}/isLiked")
    public ResponseEntity<Boolean> isPostLiked(
            @PathVariable String postId,
            @PathVariable String userId) {
        boolean isLiked = likeService.isPostLiked(postId, userId);
        return ResponseEntity.ok(isLiked);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getUserLikesCount(@PathVariable String userId) {
        long count = likeService.getUserLikesCount(userId);
        return ResponseEntity.ok(count);
    }
}
