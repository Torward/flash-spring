package ru.lomov.flash.balance.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BalanceResponse {
    private String userId;
    private BigDecimal balance;
    private String currency;
    private LocalDateTime updatedAt;
    private boolean isActive;
}
