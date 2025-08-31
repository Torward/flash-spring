package ru.lomov.flash.reels.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.reels.dto.CreateReelRequest;
import ru.lomov.flash.reels.dto.ReelResponse;
import ru.lomov.flash.reels.entities.Reel;
import ru.lomov.flash.reels.services.ReelService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reels")
@RequiredArgsConstructor
public class ReelController {

    private final ReelService reelService;

    @PostMapping("/{userId}")
    public ResponseEntity<ReelResponse> createReel(@PathVariable String userId, @RequestBody CreateReelRequest request) {
        Reel reel = mapToEntity(request, userId);
        Reel createdReel = reelService.createReel(reel);
        ReelResponse response = mapToResponse(createdReel);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{reelId}")
    public ResponseEntity<ReelResponse> getReelById(@PathVariable String reelId) {
        Reel reel = reelService.getReelById(reelId);
        ReelResponse response = mapToResponse(reel);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReelResponse>> getUserReels(@PathVariable String userId) {
        List<Reel> reels = reelService.getUserReels(userId);
        List<ReelResponse> responses = reels.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/public")
    public ResponseEntity<List<ReelResponse>> getPublicReels() {
        List<Reel> reels = reelService.getPublicReels();
        List<ReelResponse> responses = reels.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ReelResponse>> searchReels(@RequestParam String query) {
        List<Reel> reels = reelService.searchReels(query);
        List<ReelResponse> responses = reels.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{reelId}")
    public ResponseEntity<ReelResponse> updateReel(@PathVariable String reelId, @RequestBody CreateReelRequest request) {
        Reel reel = mapToEntity(request, null);
        Reel updatedReel = reelService.updateReel(reelId, reel);
        ReelResponse response = mapToResponse(updatedReel);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reelId}")
    public ResponseEntity<Void> deleteReel(@PathVariable String reelId) {
        reelService.deleteReel(reelId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reelId}/user/{userId}")
    public ResponseEntity<Void> deleteReel(@PathVariable String reelId, @PathVariable String userId) {
        reelService.deleteReel(reelId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{reelId}/like")
    public ResponseEntity<ReelResponse> likeReel(@PathVariable String reelId) {
        Reel reel = reelService.likeReel(reelId);
        ReelResponse response = mapToResponse(reel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{reelId}/unlike")
    public ResponseEntity<ReelResponse> unlikeReel(@PathVariable String reelId) {
        Reel reel = reelService.unlikeReel(reelId);
        ReelResponse response = mapToResponse(reel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{reelId}/view")
    public ResponseEntity<ReelResponse> incrementViews(@PathVariable String reelId) {
        Reel reel = reelService.incrementViews(reelId);
        ReelResponse response = mapToResponse(reel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{reelId}/share")
    public ResponseEntity<ReelResponse> incrementShares(@PathVariable String reelId) {
        Reel reel = reelService.incrementShares(reelId);
        ReelResponse response = mapToResponse(reel);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Reels Service is healthy");
    }

    private Reel mapToEntity(CreateReelRequest request, String userId) {
        Reel reel = new Reel();
        if (userId != null) {
            reel.setUserId(userId);
        }
        reel.setTitle(request.getTitle());
        reel.setDescription(request.getDescription());
        reel.setVideoUrl(request.getVideoUrl());
        reel.setThumbnailUrl(request.getThumbnailUrl());
        reel.setDurationSeconds(request.getDurationSeconds());
        reel.setHashtags(request.getHashtags());
        reel.setIsPublic(request.getIsPublic());
        return reel;
    }

    private ReelResponse mapToResponse(Reel reel) {
        ReelResponse response = new ReelResponse();
        response.setId(reel.getId());
        response.setUserId(reel.getUserId());
        response.setTitle(reel.getTitle());
        response.setDescription(reel.getDescription());
        response.setVideoUrl(reel.getVideoUrl());
        response.setThumbnailUrl(reel.getThumbnailUrl());
        response.setDurationSeconds(reel.getDurationSeconds());
        response.setHashtags(reel.getHashtags());
        response.setLikesCount(reel.getLikesCount());
        response.setCommentsCount(reel.getCommentsCount());
        response.setViewsCount(reel.getViewsCount());
        response.setSharesCount(reel.getSharesCount());
        response.setIsPublic(reel.getIsPublic());
        response.setCreatedAt(reel.getCreatedAt());
        response.setUpdatedAt(reel.getUpdatedAt());
        return response;
    }
}
