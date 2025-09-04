package ru.lomov.flashbackend.requestservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRequestDto {
    private String status;
    private String processedBy;
    private String rejectionReason;
    private String transactionId;
    private BigDecimal feeAmount;
}
