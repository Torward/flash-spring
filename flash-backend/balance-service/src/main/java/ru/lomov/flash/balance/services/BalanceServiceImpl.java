package ru.lomov.flash.balance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flash.balance.dto.BalanceResponse;
import ru.lomov.flash.balance.entities.Balance;
import ru.lomov.flash.balance.repositories.BalanceRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<BalanceResponse> getBalance(String userId) {
        return balanceRepository.findByUserId(userId)
                .map(this::mapToResponse);
    }

    @Override
    public BalanceResponse createBalance(String userId) {
        Balance balance = Balance.builder()
                .userId(userId)
                .balance(BigDecimal.ZERO)
                .build();

        Balance savedBalance = balanceRepository.save(balance);
        return mapToResponse(savedBalance);
    }

    @Override
    public BalanceResponse addToBalance(String userId, BigDecimal amount) {
        Balance balance = balanceRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Balance newBalance = Balance.builder()
                            .userId(userId)
                            .balance(BigDecimal.ZERO)
                            .build();
                    return balanceRepository.save(newBalance);
                });

        balance.setBalance(balance.getBalance().add(amount));
        Balance updatedBalance = balanceRepository.save(balance);
        return mapToResponse(updatedBalance);
    }

    @Override
    public BalanceResponse subtractFromBalance(String userId, BigDecimal amount) {
        Balance balance = balanceRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Balance not found for user: " + userId));

        if (balance.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        balance.setBalance(balance.getBalance().subtract(amount));
        Balance updatedBalance = balanceRepository.save(balance);
        return mapToResponse(updatedBalance);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasSufficientBalance(String userId, BigDecimal amount) {
        return balanceRepository.findBalanceByUserId(userId)
                .map(balance -> balance.compareTo(amount) >= 0)
                .orElse(false);
    }

    @Override
    public BalanceResponse getOrCreateBalance(String userId) {
        return balanceRepository.findByUserId(userId)
                .map(this::mapToResponse)
                .orElseGet(() -> createBalance(userId));
    }

    private BalanceResponse mapToResponse(Balance balance) {
        return BalanceResponse.builder()
                .userId(balance.getUserId())
                .balance(balance.getBalance())
                .createdAt(balance.getCreatedAt())
                .updatedAt(balance.getUpdatedAt())
                .build();
    }
}
