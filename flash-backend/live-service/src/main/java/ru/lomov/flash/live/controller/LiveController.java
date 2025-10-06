package ru.lomov.flash.live.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.live.entity.LiveStream;
import ru.lomov.flash.live.service.LiveService;

import java.util.List;

@RestController
@RequestMapping("/live")
@RequiredArgsConstructor
public class LiveController {

    private final LiveService liveService;

    @PostMapping
    public ResponseEntity<LiveStream> startLive(@RequestBody LiveStream liveStream) {
        LiveStream startedLive = liveService.startLive(liveStream);
        return ResponseEntity.ok(startedLive);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<LiveStream> getLive(@PathVariable String roomId) {
        return liveService.getLiveByRoomId(roomId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LiveStream>> getUserLiveStreams(@PathVariable String userId) {
        List<LiveStream> liveStreams = liveService.getUserLiveStreams(userId);
        return ResponseEntity.ok(liveStreams);
    }

    @GetMapping("/active")
    public ResponseEntity<List<LiveStream>> getActiveLiveStreams() {
        List<LiveStream> liveStreams = liveService.getActiveLiveStreams();
        return ResponseEntity.ok(liveStreams);
    }

    @PutMapping("/{roomId}/participant/{userId}")
    public ResponseEntity<LiveStream> addParticipant(@PathVariable String roomId, @PathVariable String userId) {
        LiveStream liveStream = liveService.addParticipant(roomId, userId);
        return liveStream != null ? ResponseEntity.ok(liveStream) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{roomId}/participant/{userId}")
    public ResponseEntity<LiveStream> removeParticipant(@PathVariable String roomId, @PathVariable String userId) {
        LiveStream liveStream = liveService.removeParticipant(roomId, userId);
        return liveStream != null ? ResponseEntity.ok(liveStream) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{roomId}/chat")
    public ResponseEntity<LiveStream> addChatMessage(@PathVariable String roomId, @RequestBody String message) {
        LiveStream liveStream = liveService.addChatMessage(roomId, message);
        return liveStream != null ? ResponseEntity.ok(liveStream) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{roomId}/viewer")
    public ResponseEntity<LiveStream> incrementViewerCount(@PathVariable String roomId) {
        LiveStream liveStream = liveService.incrementViewerCount(roomId);
        return liveStream != null ? ResponseEntity.ok(liveStream) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> endLive(@PathVariable String roomId) {
        liveService.endLive(roomId);
        return ResponseEntity.noContent().build();
    }
}
