package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.dto.ReelLikeDto;
import ru.lomov.flashbackend.entities.ReelLike;
import ru.lomov.flashbackend.services.ReelLikeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reels")
@RequiredArgsConstructor
public class ReelLikeController {

    private final ReelLikeService reelLikeService;

    @PostMapping("/{reelId}/like")
    public ResponseEntity<ReelLikeDto> likeReel(@PathVariable String reelId, @RequestParam String userId) {
        try {
            ReelLike reelLike = reelLikeService.likeReel(reelId, userId);
            ReelLikeDto dto = convertToDto(reelLike);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{reelId}/like")
    public ResponseEntity<Void> unlikeReel(@PathVariable String reelId, @RequestParam String userId) {
        try {
            reelLikeService.unlikeReel(reelId, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{reelId}/likes/count")
    public ResponseEntity<Long> getReelLikeCount(@PathVariable String reelId) {
        long count = reelLikeService.getReelLikeCount(reelId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{reelId}/likes")
    public ResponseEntity<List<ReelLikeDto>> getReelLikes(@PathVariable String reelId) {
        List<ReelLike> likes = reelLikeService.getReelLikes(reelId);
        List<ReelLikeDto> dtos = likes.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{reelId}/like")
    public ResponseEntity<ReelLikeDto> getReelLike(@PathVariable String reelId, @RequestParam String userId) {
        Optional<ReelLike> reelLike = reelLikeService.getReelLike(reelId, userId);
        if (reelLike.isPresent()) {
            ReelLikeDto dto = convertToDto(reelLike.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{reelId}/liked")
    public ResponseEntity<Boolean> hasUserLikedReel(@PathVariable String reelId, @RequestParam String userId) {
        boolean hasLiked = reelLikeService.hasUserLikedReel(reelId, userId);
        return ResponseEntity.ok(hasLiked);
    }

    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<ReelLikeDto>> getUserLikes(@PathVariable String userId) {
        List<ReelLike> likes = reelLikeService.getUserLikes(userId);
        List<ReelLikeDto> dtos = likes.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private ReelLikeDto convertToDto(ReelLike reelLike) {
        return ReelLikeDto.builder()
            .likeId(reelLike.getLikeId())
            .reelId(reelLike.getReelId())
            .userId(reelLike.getUserId())
            .likedAt(reelLike.getLikedAt())
            .build();
    }
}
