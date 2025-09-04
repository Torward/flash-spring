package ru.lomov.flashbackend.requestservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    private String requestId;
    private String userId;
    private BigDecimal amount;
    private String status;
    private String paymentMethod;
    private String accountDetails;
    private String currency;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime processedAt;
    private String processedBy;
    private String rejectionReason;
    private String transactionId;
    private BigDecimal feeAmount;
    private BigDecimal netAmount;

    // Firebase-compatible getter
    public String getId() {
        return requestId;
    }
}
