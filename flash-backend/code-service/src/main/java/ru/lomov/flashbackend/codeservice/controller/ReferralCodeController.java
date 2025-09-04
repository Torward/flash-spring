package ru.lomov.flashbackend.codeservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.codeservice.dto.ApiResponse;
import ru.lomov.flashbackend.codeservice.dto.CreateReferralCodeDto;
import ru.lomov.flashbackend.codeservice.dto.ReferralCodeDto;
import ru.lomov.flashbackend.codeservice.dto.UpdateReferralCodeDto;
import ru.lomov.flashbackend.codeservice.service.ReferralCodeService;

import java.util.List;

@RestController
@RequestMapping("/api/referral-codes")
@RequiredArgsConstructor
public class ReferralCodeController {

    private final ReferralCodeService referralCodeService;

    @PostMapping
    public ResponseEntity<ReferralCodeDto> createReferralCode(@RequestBody CreateReferralCodeDto createReferralCodeDto) {
        ReferralCodeDto referralCodeDto = referralCodeService.createReferralCode(createReferralCodeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(referralCodeDto);
    }

    @GetMapping("/{codeId}")
    public ResponseEntity<ReferralCodeDto> getReferralCode(@PathVariable String codeId) {
        ReferralCodeDto referralCodeDto = referralCodeService.getReferralCodeById(codeId);
        return ResponseEntity.ok(referralCodeDto);
    }

    @GetMapping("/code/{codeValue}")
    public ResponseEntity<ReferralCodeDto> getReferralCodeByValue(@PathVariable String codeValue) {
        ReferralCodeDto referralCodeDto = referralCodeService.getReferralCodeByValue(codeValue);
        return ResponseEntity.ok(referralCodeDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReferralCodeDto>> getUserReferralCodes(@PathVariable String userId) {
        List<ReferralCodeDto> referralCodes = referralCodeService.getReferralCodesByUserId(userId);
        return ResponseEntity.ok(referralCodes);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ReferralCodeDto>> getReferralCodesByStatus(@PathVariable String status) {
        List<ReferralCodeDto> referralCodes = referralCodeService.getReferralCodesByStatus(status);
        return ResponseEntity.ok(referralCodes);
    }

    @GetMapping
    public ResponseEntity<List<ReferralCodeDto>> getAllReferralCodes() {
        List<ReferralCodeDto> referralCodes = referralCodeService.getAllReferralCodes();
        return ResponseEntity.ok(referralCodes);
    }

    @GetMapping("/active")
    public ResponseEntity<List<ReferralCodeDto>> getActiveReferralCodes() {
        List<ReferralCodeDto> referralCodes = referralCodeService.getActiveReferralCodes();
        return ResponseEntity.ok(referralCodes);
    }

    @PutMapping("/{codeId}")
    public ResponseEntity<ReferralCodeDto> updateReferralCode(
            @PathVariable String codeId,
            @RequestBody UpdateReferralCodeDto updateReferralCodeDto) {
        ReferralCodeDto referralCodeDto = referralCodeService.updateReferralCode(codeId, updateReferralCodeDto);
        return ResponseEntity.ok(referralCodeDto);
    }

    @PostMapping("/{codeValue}/use")
    public ResponseEntity<ReferralCodeDto> useReferralCode(
            @PathVariable String codeValue,
            @RequestParam String userId) {
        ReferralCodeDto referralCodeDto = referralCodeService.useReferralCode(codeValue, userId);
        return ResponseEntity.ok(referralCodeDto);
    }

    @PostMapping("/{codeId}/expire")
    public ResponseEntity<ApiResponse> expireReferralCode(@PathVariable String codeId) {
        referralCodeService.expireReferralCode(codeId);
        return ResponseEntity.ok(new ApiResponse());
    }

    @GetMapping("/validate/{codeValue}")
    public ResponseEntity<ApiResponse> validateReferralCode(@PathVariable String codeValue) {
        boolean isValid = referralCodeService.isReferralCodeValid(codeValue);
        String message = isValid ? "Referral code is valid" : "Referral code is invalid";
        return ResponseEntity.ok(new ApiResponse(isValid ? "valid" : "invalid", message));
    }

    @DeleteMapping("/{codeId}")
    public ResponseEntity<ApiResponse> deleteReferralCode(@PathVariable String codeId) {
        referralCodeService.deleteReferralCode(codeId);
        return ResponseEntity.ok(new ApiResponse());
    }
}
