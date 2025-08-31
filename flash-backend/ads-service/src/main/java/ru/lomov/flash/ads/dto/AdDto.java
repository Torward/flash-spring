package ru.lomov.flash.ads.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdDto {

    private String id;
    private String advertiserId;
    private String title;
    private String description;
    private String imageUrl;
    private String targetUrl;
    private AdFormat format;
    private AdStatus status;
    private BigDecimal budget;
    private BigDecimal cpmRate;
    private Integer maxImpressions;
    private Integer maxClicks;
    private Integer currentImpressions;
    private Integer currentClicks;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String targetAudience;
    private LocalDateTime createdAt;
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
