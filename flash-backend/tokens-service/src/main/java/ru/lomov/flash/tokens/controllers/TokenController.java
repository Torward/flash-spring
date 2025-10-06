package ru.lomov.flash.tokens.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.tokens.dto.TokenResponse;
import ru.lomov.flash.tokens.services.TokenService;

import java.util.List;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    // Firebase-compatible endpoints
    @GetMapping("/{userId}")
    public ResponseEntity<TokenResponse> getToken(@PathVariable String userId) {
        return tokenService.getToken(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}")
    public ResponseEntity<TokenResponse> saveToken(
            @PathVariable String userId,
            @RequestParam String fcmToken,
            @RequestParam(required = false) String deviceType,
            @RequestParam(required = false) String deviceId) {
        TokenResponse response = tokenService.saveToken(userId, fcmToken, deviceType, deviceId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<TokenResponse> updateToken(
            @PathVariable String userId,
            @RequestParam String fcmToken) {
        try {
            TokenResponse response = tokenService.updateToken(userId, fcmToken);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteToken(@PathVariable String userId) {
        boolean deleted = tokenService.deleteToken(userId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}/deactivate")
    public ResponseEntity<Void> deactivateToken(
            @PathVariable String userId,
            @RequestParam String deviceId) {
        boolean deactivated = tokenService.deactivateToken(userId, deviceId);
        return deactivated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/{userId}/deactivate-all")
    public ResponseEntity<Void> deactivateAllTokens(@PathVariable String userId) {
        boolean deactivated = tokenService.deactivateAllTokens(userId);
        return deactivated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{userId}/active")
    public ResponseEntity<List<TokenResponse>> getActiveTokensByUser(@PathVariable String userId) {
        List<TokenResponse> tokens = tokenService.getActiveTokensByUser(userId);
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/active")
    public ResponseEntity<List<TokenResponse>> getAllActiveTokens() {
        List<TokenResponse> tokens = tokenService.getAllActiveTokens();
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/{userId}/count")
    public ResponseEntity<Long> getActiveTokenCount(@PathVariable String userId) {
        long count = tokenService.getActiveTokenCount(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/{userId}/has-active")
    public ResponseEntity<Boolean> hasActiveToken(@PathVariable String userId) {
        boolean hasActive = tokenService.hasActiveToken(userId);
        return ResponseEntity.ok(hasActive);
    }
}
