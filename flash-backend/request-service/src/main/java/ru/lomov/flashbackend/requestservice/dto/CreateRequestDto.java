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
public class CreateRequestDto {

    private String userId;
    private BigDecimal amount;
    private String paymentMethod;
    private String accountDetails;
    private String currency = "USD";
}
