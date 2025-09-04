package ru.lomov.flash.code.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.code.entity.ReferralCode;
import ru.lomov.flash.code.repository.ReferralCodeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReferralCodeServiceImpl implements ReferralCodeService {

    private final ReferralCodeRepository referralCodeRepository;

    @Override
    public ReferralCode createReferralCode(ReferralCode referralCode) {
        if (referralCode.getCodeValue() == null || referralCode.getCodeValue().trim().isEmpty()) {
            referralCode.setCodeValue(generateUniqueCode());
        }

        if (referralCodeRepository.existsByCodeValue(referralCode.getCodeValue())) {
            throw new RuntimeException("Referral code already exists: " + referralCode.getCodeValue());
        }

        referralCode.setCreatedAt(LocalDateTime.now());
        referralCode.setUsageCount(0);
        referralCode.setIsActive(true);

        return referralCodeRepository.save(referralCode);
    }

    @Override
    public Optional<ReferralCode> getReferralCodeById(String id) {
        return referralCodeRepository.findById(id);
    }

    @Override
    public Optional<ReferralCode> getReferralCodeByValue(String codeValue) {
        return referralCodeRepository.findByCodeValue(codeValue);
    }

    @Override
    public List<ReferralCode> getAllReferralCodes() {
        return referralCodeRepository.findAll();
    }

    @Override
    public List<ReferralCode> getReferralCodesByUser(String userId) {
        return referralCodeRepository.findByCreatedBy(userId);
    }

    @Override
    public ReferralCode updateReferralCode(String id, ReferralCode referralCode) {
        return referralCodeRepository.findById(id)
                .map(existingCode -> {
                    existingCode.setCodeValue(referralCode.getCodeValue());
                    existingCode.setExpiresAt(referralCode.getExpiresAt());
                    existingCode.setIsActive(referralCode.getIsActive());
                    existingCode.setMaxUsage(referralCode.getMaxUsage());
                    existingCode.setRewardAmount(referralCode.getRewardAmount());
                    existingCode.setDescription(referralCode.getDescription());
                    return referralCodeRepository.save(existingCode);
                })
                .orElseThrow(() -> new RuntimeException("Referral code not found"));
    }

    @Override
    public void deleteReferralCode(String id) {
        referralCodeRepository.deleteById(id);
    }

    @Override
    public boolean validateReferralCode(String codeValue) {
        Optional<ReferralCode> code = referralCodeRepository.findByCodeValue(codeValue);
        if (code.isEmpty()) {
            return false;
        }

        ReferralCode referralCode = code.get();
        return referralCode.getIsActive() &&
               (referralCode.getExpiresAt() == null || referralCode.getExpiresAt().isAfter(LocalDateTime.now())) &&
               (referralCode.getMaxUsage() == null || referralCode.getUsageCount() < referralCode.getMaxUsage());
    }

    @Override
    public ReferralCode useReferralCode(String codeValue, String userId) {
        ReferralCode code = referralCodeRepository.findByCodeValue(codeValue)
                .orElseThrow(() -> new RuntimeException("Referral code not found"));

        if (!validateReferralCode(codeValue)) {
            throw new RuntimeException("Invalid or expired referral code");
        }

        code.setUsageCount(code.getUsageCount() + 1);

        // Deactivate if max usage reached
        if (code.getMaxUsage() != null && code.getUsageCount() >= code.getMaxUsage()) {
            code.setIsActive(false);
        }

        return referralCodeRepository.save(code);
    }

    @Override
    public List<ReferralCode> getExpiredReferralCodes() {
        return referralCodeRepository.findByExpiresAtBefore(LocalDateTime.now());
    }

    @Override
    public void deactivateExpiredCodes() {
        List<ReferralCode> expiredCodes = getExpiredReferralCodes();
        for (ReferralCode code : expiredCodes) {
            code.setIsActive(false);
            referralCodeRepository.save(code);
        }
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (referralCodeRepository.existsByCodeValue(code));

        return code;
    }
}
