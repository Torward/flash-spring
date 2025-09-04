package ru.lomov.flashbackend.codeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.codeservice.entity.ReferralCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReferralCodeRepository extends JpaRepository<ReferralCode, String> {

    Optional<ReferralCode> findByCodeValue(String codeValue);

    List<ReferralCode> findByUserId(String userId);

    List<ReferralCode> findByStatus(String status);

    @Query("SELECT r FROM ReferralCode r WHERE r.expiresAt < :now AND r.status = 'ACTIVE'")
    List<ReferralCode> findExpiredCodes(LocalDateTime now);

    @Query("SELECT r FROM ReferralCode r WHERE r.status = 'ACTIVE' AND (r.expiresAt IS NULL OR r.expiresAt > :now)")
    List<ReferralCode> findActiveCodes(LocalDateTime now);

    boolean existsByCodeValue(String codeValue);
}
