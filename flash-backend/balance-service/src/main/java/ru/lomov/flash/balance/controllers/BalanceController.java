package ru.lomov.flash.balance.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.balance.dto.BalanceResponse;
import ru.lomov.flash.balance.mappers.BalanceMapper;
import ru.lomov.flash.balance.services.BalanceService;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/{userId}")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable String userId) {
        var balance = balanceService.getBalance(userId);
        return ResponseEntity.ok(BalanceMapper.toResponse(balance));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<BalanceResponse> updateBalance(
            @PathVariable String userId,
            @RequestBody BalanceResponse balanceUpdate) {
        var balance = balanceService.updateBalance(userId, balanceUpdate.getBalance());
        return ResponseEntity.ok(BalanceMapper.toResponse(balance));
    }
}
