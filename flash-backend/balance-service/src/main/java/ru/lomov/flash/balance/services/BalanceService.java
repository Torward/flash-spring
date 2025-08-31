package ru.lomov.flash.balance.services;

import ru.lomov.flash.balance.entities.Balance;

public interface BalanceService {
    Balance getBalance(String userId);
    Balance updateBalance(String userId, java.math.BigDecimal balance);
}
