package ru.lomov.flash.live.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/live")
public class LiveController {

    @PostMapping
    public ResponseEntity<String> startLive(@RequestBody Object liveData) {
        // Начать трансляцию
        return ResponseEntity.ok("Live started");
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<String> getLive(@PathVariable String roomId) {
        // Получить данные трансляции
        return ResponseEntity.ok("Live data for room: " + roomId);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> endLive(@PathVariable String roomId) {
        // Завершить трансляцию
        return ResponseEntity.noContent().build();
    }
}
