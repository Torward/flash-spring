package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.dto.ReelViewDto;
import ru.lomov.flashbackend.entities.ReelView;
import ru.lomov.flashbackend.services.ReelViewService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reels")
@RequiredArgsConstructor
public class ReelViewController {

    private final ReelViewService reelViewService;

    @PostMapping("/{reelId}/view")
    public ResponseEntity<ReelViewDto> trackReelView(
            @PathVariable String reelId,
            @RequestParam String userId,
            @RequestParam(required = false) Integer viewDuration,
            @RequestParam(required = false) String deviceInfo) {
        ReelView reelView = reelViewService.trackReelView(reelId, userId, viewDuration, deviceInfo);
        ReelViewDto dto = convertToDto(reelView);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{reelId}/views/count")
    public ResponseEntity<Long> getReelViewCount(@PathVariable String reelId) {
        long count = reelViewService.getReelViewCount(reelId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{reelId}/views")
    public ResponseEntity<List<ReelViewDto>> getReelViews(@PathVariable String reelId) {
        List<ReelView> views = reelViewService.getReelViews(reelId);
        List<ReelViewDto> dtos = views.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{reelId}/view")
    public ResponseEntity<ReelViewDto> getReelView(@PathVariable String reelId, @RequestParam String userId) {
        try {
            ReelView reelView = reelViewService.getReelView(reelId, userId);
            ReelViewDto dto = convertToDto(reelView);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{reelId}/viewed")
    public ResponseEntity<Boolean> hasUserViewedReel(@PathVariable String reelId, @RequestParam String userId) {
        boolean hasViewed = reelViewService.hasUserViewedReel(reelId, userId);
        return ResponseEntity.ok(hasViewed);
    }

    @GetMapping("/user/{userId}/views")
    public ResponseEntity<List<ReelViewDto>> getUserViews(@PathVariable String userId) {
        List<ReelView> views = reelViewService.getUserViews(userId);
        List<ReelViewDto> dtos = views.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{reelId}/views/average-duration")
    public ResponseEntity<Double> getAverageViewDuration(@PathVariable String reelId) {
        Double averageDuration = reelViewService.getAverageViewDuration(reelId);
        return ResponseEntity.ok(averageDuration);
    }

    @GetMapping("/{reelId}/views/total-time")
    public ResponseEntity<Long> getTotalViewTime(@PathVariable String reelId) {
        Long totalTime = reelViewService.getTotalViewTime(reelId);
        return ResponseEntity.ok(totalTime);
    }

    private ReelViewDto convertToDto(ReelView reelView) {
        return ReelViewDto.builder()
            .viewId(reelView.getViewId())
            .reelId(reelView.getReelId())
            .userId(reelView.getUserId())
            .viewedAt(reelView.getViewedAt())
            .viewDuration(reelView.getViewDuration())
            .deviceInfo(reelView.getDeviceInfo())
            .build();
    }
}
