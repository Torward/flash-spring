package ru.lomov.flash.ads.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ads")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ad {

    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String advertiserId; // ID of the user/advertiser

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column
    private String imageUrl;

    @Column
    private String targetUrl; // URL to redirect when clicked

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdFormat format; // BANNER, INTERSTITIAL, NATIVE, VIDEO

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdStatus status; // ACTIVE, INACTIVE, PENDING, REJECTED

    @Column(precision = 10, scale = 2)
    private BigDecimal budget; // Total budget for the campaign

    @Column(precision = 10, scale = 2)
    private BigDecimal cpmRate; // Cost per thousand impressions

    @Column
    private Integer maxImpressions; // Maximum impressions allowed

    @Column
    private Integer maxClicks; // Maximum clicks allowed

    @Column
    private Integer currentImpressions = 0;

    @Column
    private Integer currentClicks = 0;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private String targetAudience; // JSON string for targeting criteria

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum AdFormat {
        BANNER,
        INTERSTITIAL,
        NATIVE,
        VIDEO
    }

    public enum AdStatus {
        ACTIVE,
        INACTIVE,
        PENDING,
        REJECTED,
        EXPIRED
    }
}
