package ru.lomov.flash.podcast.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/podcast")
public class PodcastController {

    @PostMapping
    public ResponseEntity<String> startPodcast(@RequestBody Object podcastData) {
        // Начать подкаст
        return ResponseEntity.ok("Podcast started");
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<String> getPodcast(@PathVariable String roomId) {
        // Получить данные подкаста
        return ResponseEntity.ok("Podcast data for room: " + roomId);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> endPodcast(@PathVariable String roomId) {
        // Завершить подкаст
        return ResponseEntity.noContent().build();
    }
}
