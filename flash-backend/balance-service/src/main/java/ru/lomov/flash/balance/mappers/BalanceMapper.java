package ru.lomov.flash.balance.mappers;

import ru.lomov.flash.balance.dto.BalanceResponse;
import ru.lomov.flash.balance.entities.Balance;

public class BalanceMapper {
    
    public static BalanceResponse toResponse(Balance balance) {
        BalanceResponse response = new BalanceResponse();
        response.setUserId(balance.getUserId());
        response.setBalance(balance.getBalance());
        response.setCurrency(balance.getCurrency());
        response.setUpdatedAt(balance.getUpdatedAt());
        response.setActive(balance.getIsActive());
        return response;
    }
}
