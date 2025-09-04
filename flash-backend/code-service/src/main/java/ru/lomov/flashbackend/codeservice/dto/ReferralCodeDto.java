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
public class ReferralCodeDto {
    private String codeId;
    private String codeValue;
    private String userId;
    private BigDecimal rewardAmount;
    private Integer maxUsage;
    private Integer currentUsage;
    private String status;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private Boolean canBeUsed;
}
