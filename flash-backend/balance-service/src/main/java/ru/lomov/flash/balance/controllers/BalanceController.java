package ru.lomov.flash.balance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.balance.dto.BalanceResponse;
import ru.lomov.flash.balance.services.BalanceService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    // Firebase-compatible endpoints
    @GetMapping("/{userId}")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable String userId) {
        return balanceService.getBalance(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{userId}")
    public ResponseEntity<BalanceResponse> createBalance(@PathVariable String userId) {
        BalanceResponse response = balanceService.createBalance(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}/add")
    public ResponseEntity<BalanceResponse> addToBalance(
            @PathVariable String userId,
            @RequestParam BigDecimal amount) {
        BalanceResponse response = balanceService.addToBalance(userId, amount);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}/subtract")
    public ResponseEntity<BalanceResponse> subtractFromBalance(
            @PathVariable String userId,
            @RequestParam BigDecimal amount) {
        try {
            BalanceResponse response = balanceService.subtractFromBalance(userId, amount);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}/sufficient")
    public ResponseEntity<Boolean> hasSufficientBalance(
            @PathVariable String userId,
            @RequestParam BigDecimal amount) {
        boolean hasSufficient = balanceService.hasSufficientBalance(userId, amount);
        return ResponseEntity.ok(hasSufficient);
    }

    @GetMapping("/{userId}/or-create")
    public ResponseEntity<BalanceResponse> getOrCreateBalance(@PathVariable String userId) {
        BalanceResponse response = balanceService.getOrCreateBalance(userId);
        return ResponseEntity.ok(response);
    }
}
