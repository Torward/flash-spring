package ru.lomov.flash.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.follow.dto.FollowResponse;
import ru.lomov.flash.follow.services.FollowService;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}/following/{targetUserId}")
    public ResponseEntity<FollowResponse> followUser(
            @PathVariable String userId,
            @PathVariable String targetUserId) {
        FollowResponse response = followService.followUser(userId, targetUserId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/following/{targetUserId}")
    public ResponseEntity<FollowResponse> unfollowUser(
            @PathVariable String userId,
            @PathVariable String targetUserId) {
        FollowResponse response = followService.unfollowUser(userId, targetUserId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<FollowResponse>> getFollowing(@PathVariable String userId) {
        List<FollowResponse> following = followService.getFollowing(userId);
        return ResponseEntity.ok(following);
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<FollowResponse>> getFollowers(@PathVariable String userId) {
        List<FollowResponse> followers = followService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{userId}/following/count")
    public ResponseEntity<Long> getFollowingCount(@PathVariable String userId) {
        long count = followService.getFollowingCount(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<Long> getFollowersCount(@PathVariable String userId) {
        long count = followService.getFollowersCount(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{userId}/isFollowing/{targetUserId}")
    public ResponseEntity<Boolean> isFollowing(
            @PathVariable String userId,
            @PathVariable String targetUserId) {
        boolean isFollowing = followService.isFollowing(userId, targetUserId);
        return ResponseEntity.ok(isFollowing);
    }

    @GetMapping("/{userId}/mutual/{targetUserId}")
    public ResponseEntity<List<FollowResponse>> getMutualFollowers(
            @PathVariable String userId,
            @PathVariable String targetUserId) {
        List<FollowResponse> mutualFollowers = followService.getMutualFollowers(userId, targetUserId);
        return ResponseEntity.ok(mutualFollowers);
    }
}
