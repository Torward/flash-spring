package ru.lomov.flash.ads.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdRequestDto {

    @NotBlank(message = "Advertiser ID is required")
    private String advertiserId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String imageUrl;

    @NotBlank(message = "Target URL is required")
    private String targetUrl;

    @NotNull(message = "Ad format is required")
    private AdFormat format;

    @NotNull(message = "Budget is required")
    @DecimalMin(value = "0.01", message = "Budget must be greater than 0")
    private BigDecimal budget;

    @NotNull(message = "CPM rate is required")
    @DecimalMin(value = "0.01", message = "CPM rate must be greater than 0")
    private BigDecimal cpmRate;

    @Min(value = 1, message = "Max impressions must be at least 1")
    private Integer maxImpressions;

    @Min(value = 1, message = "Max clicks must be at least 1")
    private Integer maxClicks;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String targetAudience;

    public enum AdFormat {
        BANNER,
        INTERSTITIAL,
        NATIVE,
        VIDEO
    }
}
