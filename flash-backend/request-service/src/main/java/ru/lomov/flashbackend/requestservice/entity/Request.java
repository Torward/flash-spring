package ru.lomov.flashbackend.requestservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
@NoArgsConstructor
public class Request {
    @Id
    @UuidGenerator
    @Column(name = "request_id", nullable = false, unique = true, updatable = false)
    private String requestId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status = "PENDING"; // PENDING, APPROVED, REJECTED, COMPLETED

    @Column(name = "payment_method")
    private String paymentMethod; // BANK_TRANSFER, PAYPAL, CRYPTO, etc.

    @Column(name = "account_details", columnDefinition = "TEXT")
    private String accountDetails; // JSON string with account information

    @Column(name = "currency", nullable = false)
    private String currency = "USD";

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "processed_by")
    private String processedBy; // Admin user ID who processed the request

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "transaction_id")
    private String transactionId; // External payment processor transaction ID

    @Column(name = "fee_amount", precision = 10, scale = 2)
    private BigDecimal feeAmount = BigDecimal.ZERO;

    @Column(name = "net_amount", precision = 15, scale = 2)
    private BigDecimal netAmount;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    // Firebase-compatible getters and setters
    public String getId() {
        return requestId;
    }

    public void setId(String requestId) {
        this.requestId = requestId;
    }

    // Pre-persist callback to calculate net amount
    @PrePersist
    @PreUpdate
    private void calculateNetAmount() {
        if (amount != null && feeAmount != null) {
            this.netAmount = amount.subtract(feeAmount);
        }
    }

    // Status constants
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_APPROVED = "APPROVED";
    public static final String STATUS_REJECTED = "REJECTED";
    public static final String STATUS_COMPLETED = "COMPLETED";

    // Payment method constants
    public static final String PAYMENT_BANK_TRANSFER = "BANK_TRANSFER";
    public static final String PAYMENT_PAYPAL = "PAYPAL";
    public static final String PAYMENT_CRYPTO = "CRYPTO";
    public static final String PAYMENT_WALLET = "WALLET";
}
