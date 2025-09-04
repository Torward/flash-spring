package ru.lomov.flash.code.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.code.entity.ReferralCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReferralCodeRepository extends JpaRepository<ReferralCode, String> {
    Optional<ReferralCode> findByCodeValue(String codeValue);
    List<ReferralCode> findByCreatedBy(String createdBy);
    List<ReferralCode> findByIsActiveTrue();
    List<ReferralCode> findByExpiresAtBefore(LocalDateTime dateTime);
    boolean existsByCodeValue(String codeValue);
}
