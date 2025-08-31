package ru.lomov.flash.ads.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.ads.entities.Ad;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<ru.lomov.flash.ads.entities.Ad, String> {

    // Find all ads by advertiser
    List<Ad> findByAdvertiserId(String advertiserId);

    // Find active ads for serving
    @Query("SELECT a FROM Ad a WHERE a.status = 'ACTIVE' AND a.startDate <= :now AND (a.endDate IS NULL OR a.endDate >= :now) AND a.currentImpressions < a.maxImpressions")
    List<Ad> findActiveAds(@Param("now") LocalDateTime now);

    // Find ads by status
    List<Ad> findByStatus(Ad.AdStatus status);

    // Find ads by format
    List<Ad> findByFormat(Ad.AdFormat format);

    // Find ads within budget range
    @Query("SELECT a FROM Ad a WHERE a.budget BETWEEN :minBudget AND :maxBudget")
    List<Ad> findByBudgetRange(@Param("minBudget") java.math.BigDecimal minBudget, @Param("maxBudget") java.math.BigDecimal maxBudget);

    // Find ads by date range
    @Query("SELECT a FROM Ad a WHERE a.createdAt BETWEEN :startDate AND :endDate")
    List<Ad> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Count ads by advertiser
    Long countByAdvertiserId(String advertiserId);

    // Count active ads
    @Query("SELECT COUNT(a) FROM Ad a WHERE a.status = 'ACTIVE'")
    Long countActiveAds();

    // Find ads that need budget updates (low budget warning)
    @Query("SELECT a FROM Ad a WHERE a.status = 'ACTIVE' AND (a.budget * 0.1) > (a.currentImpressions * a.cpmRate / 1000)")
    List<Ad> findAdsWithLowBudget();

    // Find expired ads
    @Query("SELECT a FROM Ad a WHERE a.endDate < :now AND a.status = 'ACTIVE'")
    List<Ad> findExpiredAds(@Param("now") LocalDateTime now);
}
