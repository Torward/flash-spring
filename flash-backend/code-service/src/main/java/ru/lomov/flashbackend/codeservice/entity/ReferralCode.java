package ru.lomov.flashbackend.codeservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "referral_codes")
public class ReferralCode {

    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_EXPIRED = "EXPIRED";

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "code_id", updatable = false, nullable = false)
    private String codeId;

    @Column(name = "code_value", unique = true, nullable = false, length = 50)
    private String codeValue;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "reward_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal rewardAmount;

    @Column(name = "max_usage", nullable = false)
    private Integer maxUsage;

    @Column(name = "current_usage", nullable = false)
    private Integer currentUsage = 0;

    @Column(name = "status", nullable = false, length = 20)
    private String status = STATUS_ACTIVE;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Business methods
    public boolean isActive() {
        return STATUS_ACTIVE.equals(status) &&
               (expiresAt == null || expiresAt.isAfter(LocalDateTime.now()));
    }

    public boolean canBeUsed() {
        return isActive() && currentUsage < maxUsage;
    }

    public void incrementUsage() {
        if (canBeUsed()) {
            currentUsage++;
            if (currentUsage >= maxUsage) {
                status = STATUS_INACTIVE;
            }
        }
    }

    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(LocalDateTime.now());
    }

    public void expire() {
        status = STATUS_EXPIRED;
    }
}
