package ru.lomov.flashbackend.codeservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateReferralCodeDto {
    private String status;
    private BigDecimal rewardAmount;
    private Integer maxUsage;
    private LocalDateTime expiresAt;
}
