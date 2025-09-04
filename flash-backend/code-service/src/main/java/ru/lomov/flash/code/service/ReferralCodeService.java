package ru.lomov.flash.code.service;

import ru.lomov.flash.code.entity.ReferralCode;

import java.util.List;
import java.util.Optional;

public interface ReferralCodeService {
    ReferralCode createReferralCode(ReferralCode referralCode);
    Optional<ReferralCode> getReferralCodeById(String id);
    Optional<ReferralCode> getReferralCodeByValue(String codeValue);
    List<ReferralCode> getAllReferralCodes();
    List<ReferralCode> getReferralCodesByUser(String userId);
    ReferralCode updateReferralCode(String id, ReferralCode referralCode);
    void deleteReferralCode(String id);
    boolean validateReferralCode(String codeValue);
    ReferralCode useReferralCode(String codeValue, String userId);
    List<ReferralCode> getExpiredReferralCodes();
    void deactivateExpiredCodes();
}
