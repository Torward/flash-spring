package ru.lomov.flash.podcast.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.podcast.entity.Podcast;
import ru.lomov.flash.podcast.service.PodcastService;

import java.util.List;

@RestController
@RequestMapping("/podcast")
@RequiredArgsConstructor
public class PodcastController {

    private final PodcastService podcastService;

    @PostMapping
    public ResponseEntity<Podcast> startPodcast(@RequestBody Podcast podcast) {
        Podcast startedPodcast = podcastService.startPodcast(podcast);
        return ResponseEntity.ok(startedPodcast);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Podcast> getPodcast(@PathVariable String roomId) {
        return podcastService.getPodcastByRoomId(roomId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Podcast>> getUserPodcasts(@PathVariable String userId) {
        List<Podcast> podcasts = podcastService.getUserPodcasts(userId);
        return ResponseEntity.ok(podcasts);
    }

    @GetMapping("/active")
    public ResponseEntity<List<Podcast>> getActivePodcasts() {
        List<Podcast> podcasts = podcastService.getActivePodcasts();
        return ResponseEntity.ok(podcasts);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Podcast>> getPodcastsByCategory(@PathVariable String category) {
        List<Podcast> podcasts = podcastService.getPodcastsByCategory(category);
        return ResponseEntity.ok(podcasts);
    }

    @PutMapping("/{roomId}/participant/{userId}")
    public ResponseEntity<Podcast> addParticipant(@PathVariable String roomId, @PathVariable String userId) {
        Podcast podcast = podcastService.addParticipant(roomId, userId);
        return podcast != null ? ResponseEntity.ok(podcast) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{roomId}/participant/{userId}")
    public ResponseEntity<Podcast> removeParticipant(@PathVariable String roomId, @PathVariable String userId) {
        Podcast podcast = podcastService.removeParticipant(roomId, userId);
        return podcast != null ? ResponseEntity.ok(podcast) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{roomId}/chat")
    public ResponseEntity<Podcast> addChatMessage(@PathVariable String roomId, @RequestBody String message) {
        Podcast podcast = podcastService.addChatMessage(roomId, message);
        return podcast != null ? ResponseEntity.ok(podcast) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{roomId}/listener")
    public ResponseEntity<Podcast> incrementListenerCount(@PathVariable String roomId) {
        Podcast podcast = podcastService.incrementListenerCount(roomId);
        return podcast != null ? ResponseEntity.ok(podcast) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> endPodcast(@PathVariable String roomId) {
        podcastService.endPodcast(roomId);
        return ResponseEntity.noContent().build();
    }
}
