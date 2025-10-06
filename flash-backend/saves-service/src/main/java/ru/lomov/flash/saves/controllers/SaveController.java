package ru.lomov.flash.saves.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.saves.dto.SaveResponse;
import ru.lomov.flash.saves.services.SaveService;

import java.util.List;

@RestController
@RequestMapping("/saves")
@RequiredArgsConstructor
public class SaveController {

    private final SaveService saveService;

    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<SaveResponse> savePost(
            @PathVariable String postId,
            @PathVariable String userId) {
        SaveResponse response = saveService.savePost(postId, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}/{userId}")
    public ResponseEntity<SaveResponse> unsavePost(
            @PathVariable String postId,
            @PathVariable String userId) {
        SaveResponse response = saveService.unsavePost(postId, userId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{postId}/{userId}")
    public ResponseEntity<SaveResponse> getSave(
            @PathVariable String postId,
            @PathVariable String userId) {
        SaveResponse response = saveService.getSave(postId, userId);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SaveResponse>> getUserSaves(
            @PathVariable String userId) {
        List<SaveResponse> saves = saveService.getUserSaves(userId);
        return ResponseEntity.ok(saves);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<SaveResponse>> getPostSaves(
            @PathVariable String postId) {
        List<SaveResponse> saves = saveService.getPostSaves(postId);
        return ResponseEntity.ok(saves);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> getUserSavesCount(
            @PathVariable String userId) {
        long count = saveService.getUserSavesCount(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> getPostSavesCount(
            @PathVariable String postId) {
        long count = saveService.getPostSavesCount(postId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{postId}/{userId}/isSaved")
    public ResponseEntity<Boolean> isPostSaved(
            @PathVariable String postId,
            @PathVariable String userId) {
        boolean isSaved = saveService.isPostSaved(postId, userId);
        return ResponseEntity.ok(isSaved);
    }


}
