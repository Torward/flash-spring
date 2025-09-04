package ru.lomov.flashbackend.codeservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flashbackend.codeservice.dto.CreateReferralCodeDto;
import ru.lomov.flashbackend.codeservice.dto.ReferralCodeDto;
import ru.lomov.flashbackend.codeservice.dto.UpdateReferralCodeDto;
import ru.lomov.flashbackend.codeservice.entity.ReferralCode;
import ru.lomov.flashbackend.codeservice.exception.ReferralCodeNotFoundException;
import ru.lomov.flashbackend.codeservice.exception.InvalidReferralCodeException;
import ru.lomov.flashbackend.codeservice.repository.ReferralCodeRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReferralCodeServiceImpl implements ReferralCodeService {

    private final ReferralCodeRepository referralCodeRepository;

    @Override
    public ReferralCodeDto createReferralCode(CreateReferralCodeDto createReferralCodeDto) {
        // Generate unique code value if not provided
        String codeValue = createReferralCodeDto.getCodeValue();
        if (codeValue == null || codeValue.trim().isEmpty()) {
            codeValue = generateUniqueCodeValue();
        }

        // Check if code value already exists
        if (referralCodeRepository.existsByCodeValue(codeValue)) {
            throw new InvalidReferralCodeException("Referral code already exists: " + codeValue);
        }

        ReferralCode referralCode = new ReferralCode();
        referralCode.setCodeValue(codeValue);
        referralCode.setUserId(createReferralCodeDto.getUserId());
        referralCode.setRewardAmount(createReferralCodeDto.getRewardAmount());
        referralCode.setMaxUsage(createReferralCodeDto.getMaxUsage() != null ? createReferralCodeDto.getMaxUsage() : 1);
        referralCode.setExpiresAt(createReferralCodeDto.getExpiresAt());

        ReferralCode savedReferralCode = referralCodeRepository.save(referralCode);
        return convertToDto(savedReferralCode);
    }

    @Override
    @Transactional(readOnly = true)
    public ReferralCodeDto getReferralCodeById(String codeId) {
        ReferralCode referralCode = referralCodeRepository.findById(codeId)
                .orElseThrow(() -> new ReferralCodeNotFoundException("Referral code not found with id: " + codeId));
        return convertToDto(referralCode);
    }

    @Override
    @Transactional(readOnly = true)
    public ReferralCodeDto getReferralCodeByValue(String codeValue) {
        ReferralCode referralCode = referralCodeRepository.findByCodeValue(codeValue)
                .orElseThrow(() -> new ReferralCodeNotFoundException("Referral code not found with value: " + codeValue));
        return convertToDto(referralCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferralCodeDto> getReferralCodesByUserId(String userId) {
        return referralCodeRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferralCodeDto> getReferralCodesByStatus(String status) {
        return referralCodeRepository.findByStatus(status)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferralCodeDto> getAllReferralCodes() {
        return referralCodeRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReferralCodeDto updateReferralCode(String codeId, UpdateReferralCodeDto updateReferralCodeDto) {
        ReferralCode referralCode = referralCodeRepository.findById(codeId)
                .orElseThrow(() -> new ReferralCodeNotFoundException("Referral code not found with id: " + codeId));

        if (updateReferralCodeDto.getStatus() != null) {
            referralCode.setStatus(updateReferralCodeDto.getStatus());
        }

        if (updateReferralCodeDto.getRewardAmount() != null) {
            referralCode.setRewardAmount(updateReferralCodeDto.getRewardAmount());
        }

        if (updateReferralCodeDto.getMaxUsage() != null) {
            referralCode.setMaxUsage(updateReferralCodeDto.getMaxUsage());
        }

        if (updateReferralCodeDto.getExpiresAt() != null) {
            referralCode.setExpiresAt(updateReferralCodeDto.getExpiresAt());
        }

        ReferralCode updatedReferralCode = referralCodeRepository.save(referralCode);
        return convertToDto(updatedReferralCode);
    }

    @Override
    public void deleteReferralCode(String codeId) {
        if (!referralCodeRepository.existsById(codeId)) {
            throw new ReferralCodeNotFoundException("Referral code not found with id: " + codeId);
        }
        referralCodeRepository.deleteById(codeId);
    }

    @Override
    public ReferralCodeDto useReferralCode(String codeValue, String userId) {
        ReferralCode referralCode = referralCodeRepository.findByCodeValue(codeValue)
                .orElseThrow(() -> new ReferralCodeNotFoundException("Referral code not found: " + codeValue));

        if (!referralCode.canBeUsed()) {
            throw new InvalidReferralCodeException("Referral code cannot be used: " + codeValue);
        }

        referralCode.incrementUsage();
        ReferralCode updatedReferralCode = referralCodeRepository.save(referralCode);
        return convertToDto(updatedReferralCode);
    }

    @Override
    public void expireReferralCode(String codeId) {
        ReferralCode referralCode = referralCodeRepository.findById(codeId)
                .orElseThrow(() -> new ReferralCodeNotFoundException("Referral code not found with id: " + codeId));

        referralCode.expire();
        referralCodeRepository.save(referralCode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReferralCodeDto> getActiveReferralCodes() {
        return referralCodeRepository.findActiveCodes(LocalDateTime.now())
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isReferralCodeValid(String codeValue) {
        return referralCodeRepository.findByCodeValue(codeValue)
                .map(ReferralCode::canBeUsed)
                .orElse(false);
    }

    private String generateUniqueCodeValue() {
        String codeValue;
        do {
            codeValue = "REF" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (referralCodeRepository.existsByCodeValue(codeValue));
        return codeValue;
    }

    private ReferralCodeDto convertToDto(ReferralCode referralCode) {
        return ReferralCodeDto.builder()
                .codeId(referralCode.getCodeId())
                .codeValue(referralCode.getCodeValue())
                .userId(referralCode.getUserId())
                .rewardAmount(referralCode.getRewardAmount())
                .maxUsage(referralCode.getMaxUsage())
                .currentUsage(referralCode.getCurrentUsage())
                .status(referralCode.getStatus())
                .expiresAt(referralCode.getExpiresAt())
                .createdAt(referralCode.getCreatedAt())
                .updatedAt(referralCode.getUpdatedAt())
                .isActive(referralCode.isActive())
                .canBeUsed(referralCode.canBeUsed())
                .build();
    }
}
