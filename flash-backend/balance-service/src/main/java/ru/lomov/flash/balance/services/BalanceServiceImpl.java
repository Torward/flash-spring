package ru.lomov.flash.balance.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flash.balance.entities.Balance;
import ru.lomov.flash.balance.repositories.BalanceRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;

    @Override
    public Balance getBalance(String userId) {
        return balanceRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Balance balance = new Balance();
                    balance.setUserId(userId);
                    balance.setBalance(BigDecimal.ZERO);
                    return balanceRepository.save(balance);
                });
    }

    @Override
    @Transactional
    public Balance updateBalance(String userId, BigDecimal balanceValue) {
        Balance balance = getBalance(userId);
        balance.setBalance(balanceValue);
        return balanceRepository.save(balance);
    }
}
