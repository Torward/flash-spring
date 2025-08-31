package ru.lomov.flash.tokens.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.tokens.dto.TokenRequest;
import ru.lomov.flash.tokens.entities.Token;
import ru.lomov.flash.tokens.services.TokenService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/{userId}")
    public ResponseEntity<Token> saveToken(
            @PathVariable String userId,
            @RequestBody TokenRequest tokenRequest) {
        Token savedToken = tokenService.saveToken(userId, tokenRequest);
        return ResponseEntity.ok(savedToken);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Token> getToken(@PathVariable String userId) {
        Optional<Token> token = tokenService.getTokenByUserId(userId);
        return token.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Token> updateToken(
            @PathVariable String userId,
            @RequestBody TokenRequest tokenRequest) {
        tokenService.updateToken(userId, tokenRequest);
        Optional<Token> updatedToken = tokenService.getTokenByUserId(userId);
        return updatedToken.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteToken(
            @PathVariable String userId,
            @RequestParam(required = false) String token) {
        if (token != null) {
            tokenService.deleteToken(userId, token);
        } else {
            tokenService.deleteAllTokensByUserId(userId);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/all")
    public ResponseEntity<List<Token>> getAllTokens(@PathVariable String userId) {
        List<Token> tokens = tokenService.getAllTokensByUserId(userId);
        return ResponseEntity.ok(tokens);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> checkTokenExists(
            @RequestParam String userId,
            @RequestParam String token) {
        boolean exists = tokenService.existsByUserIdAndToken(userId, token);
        return ResponseEntity.ok(exists);
    }
}
