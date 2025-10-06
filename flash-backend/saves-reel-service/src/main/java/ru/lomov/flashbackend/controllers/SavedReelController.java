package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.dto.SavedReelDto;
import ru.lomov.flashbackend.entities.SavedReel;
import ru.lomov.flashbackend.services.SavedReelService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/saved-reels")
@RequiredArgsConstructor
public class SavedReelController {

    private final SavedReelService savedReelService;

    @PostMapping
    public ResponseEntity<SavedReelDto> saveReel(@RequestParam String userId, @RequestParam String reelId) {
        try {
            SavedReel savedReel = savedReelService.saveReel(userId, reelId);
            SavedReelDto dto = convertToDto(savedReel);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> unsaveReel(@RequestParam String userId, @RequestParam String reelId) {
        try {
            savedReelService.unsaveReel(userId, reelId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isReelSavedByUser(@RequestParam String userId, @RequestParam String reelId) {
        boolean isSaved = savedReelService.isReelSavedByUser(userId, reelId);
        return ResponseEntity.ok(isSaved);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SavedReelDto>> getUserSavedReels(@PathVariable String userId) {
        List<SavedReel> savedReels = savedReelService.getUserSavedReels(userId);
        List<SavedReelDto> dtos = savedReels.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/reel/{reelId}/savers")
    public ResponseEntity<List<SavedReelDto>> getReelSavers(@PathVariable String reelId) {
        List<SavedReel> savers = savedReelService.getReelSavers(reelId);
        List<SavedReelDto> dtos = savers.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/reel/{reelId}/count")
    public ResponseEntity<Long> getReelSaveCount(@PathVariable String reelId) {
        long count = savedReelService.getReelSaveCount(reelId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getUserSaveCount(@PathVariable String userId) {
        long count = savedReelService.getUserSaveCount(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping
    public ResponseEntity<SavedReelDto> getSavedReel(@RequestParam String userId, @RequestParam String reelId) {
        try {
            SavedReel savedReel = savedReelService.getSavedReel(userId, reelId);
            SavedReelDto dto = convertToDto(savedReel);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    private SavedReelDto convertToDto(SavedReel savedReel) {
        return SavedReelDto.builder()
            .saveId(savedReel.getSaveId())
            .userId(savedReel.getUserId())
            .reelId(savedReel.getReelId())
            .savedAt(savedReel.getSavedAt())
            .build();
    }
}
