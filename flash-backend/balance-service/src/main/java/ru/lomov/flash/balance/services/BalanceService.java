package ru.lomov.flash.balance.services;

import ru.lomov.flash.balance.dto.BalanceResponse;

import java.math.BigDecimal;
import java.util.Optional;

public interface BalanceService {

    // Firebase-compatible methods
    Optional<BalanceResponse> getBalance(String userId);

    BalanceResponse createBalance(String userId);

    BalanceResponse addToBalance(String userId, BigDecimal amount);

    BalanceResponse subtractFromBalance(String userId, BigDecimal amount);

    boolean hasSufficientBalance(String userId, BigDecimal amount);

    // Additional utility methods
    BalanceResponse getOrCreateBalance(String userId);
}
