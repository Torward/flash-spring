package ru.lomov.flash.calling.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.calling.dto.CallDto;
import ru.lomov.flash.calling.dto.CallRequestDto;
import ru.lomov.flash.calling.services.CallService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calling")
@RequiredArgsConstructor
public class CallController {

    private final CallService callService;

    @PostMapping
    public ResponseEntity<CallDto> initiateCall(@Valid @RequestBody CallRequestDto callRequest) {
        CallDto call = callService.initiateCall(callRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(call);
    }

    @GetMapping("/{callId}")
    public ResponseEntity<CallDto> getCallById(@PathVariable String callId) {
        Optional<CallDto> call = callService.getCallById(callId);
        return call.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CallDto>> getCallsForUser(@PathVariable String userId) {
        List<CallDto> calls = callService.getCallsForUser(userId);
        return ResponseEntity.ok(calls);
    }

    @PutMapping("/{callId}/status")
    public ResponseEntity<CallDto> updateCallStatus(
            @PathVariable String callId,
            @RequestParam String status) {
        CallDto call = callService.updateCallStatus(callId, status);
        return ResponseEntity.ok(call);
    }

    @PutMapping("/{callId}/end")
    public ResponseEntity<Void> endCall(
            @PathVariable String callId,
            @RequestParam String endReason) {
        callService.endCall(callId, endReason);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{callId}")
    public ResponseEntity<Void> deleteCall(@PathVariable String callId) {
        callService.deleteCall(callId);
        return ResponseEntity.noContent().build();
    }
}
