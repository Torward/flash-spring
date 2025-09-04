package ru.lomov.flashbackend.codeservice.service;

import ru.lomov.flashbackend.codeservice.dto.CreateReferralCodeDto;
import ru.lomov.flashbackend.codeservice.dto.ReferralCodeDto;
import ru.lomov.flashbackend.codeservice.dto.UpdateReferralCodeDto;

import java.util.List;

public interface ReferralCodeService {
    ReferralCodeDto createReferralCode(CreateReferralCodeDto createReferralCodeDto);
    ReferralCodeDto getReferralCodeById(String codeId);
    ReferralCodeDto getReferralCodeByValue(String codeValue);
    List<ReferralCodeDto> getReferralCodesByUserId(String userId);
    List<ReferralCodeDto> getReferralCodesByStatus(String status);
    List<ReferralCodeDto> getAllReferralCodes();
    ReferralCodeDto updateReferralCode(String codeId, UpdateReferralCodeDto updateReferralCodeDto);
    void deleteReferralCode(String codeId);
    ReferralCodeDto useReferralCode(String codeValue, String userId);
    void expireReferralCode(String codeId);
    List<ReferralCodeDto> getActiveReferralCodes();
    boolean isReferralCodeValid(String codeValue);
}
