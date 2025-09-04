package ru.lomov.flash.code.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.code.entity.ReferralCode;
import ru.lomov.flash.code.service.ReferralCodeService;

import java.util.List;

@RestController
@RequestMapping("/codes")
@RequiredArgsConstructor
public class ReferralCodeController {

    private final ReferralCodeService referralCodeService;

    @PostMapping
    public ResponseEntity<ReferralCode> createReferralCode(@RequestBody ReferralCode referralCode) {
        ReferralCode createdCode = referralCodeService.createReferralCode(referralCode);
        return ResponseEntity.ok(createdCode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReferralCode> getReferralCodeById(@PathVariable String id) {
        return referralCodeService.getReferralCodeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/value/{codeValue}")
    public ResponseEntity<ReferralCode> getReferralCodeByValue(@PathVariable String codeValue) {
        return referralCodeService.getReferralCodeByValue(codeValue)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ReferralCode>> getAllReferralCodes() {
        List<ReferralCode> codes = referralCodeService.getAllReferralCodes();
        return ResponseEntity.ok(codes);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReferralCode>> getReferralCodesByUser(@PathVariable String userId) {
        List<ReferralCode> codes = referralCodeService.getReferralCodesByUser(userId);
        return ResponseEntity.ok(codes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReferralCode> updateReferralCode(@PathVariable String id, @RequestBody ReferralCode referralCode) {
        try {
            ReferralCode updatedCode = referralCodeService.updateReferralCode(id, referralCode);
            return ResponseEntity.ok(updatedCode);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReferralCode(@PathVariable String id) {
        try {
            referralCodeService.deleteReferralCode(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/validate/{codeValue}")
    public ResponseEntity<Boolean> validateReferralCode(@PathVariable String codeValue) {
        boolean isValid = referralCodeService.validateReferralCode(codeValue);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/use/{codeValue}")
    public ResponseEntity<ReferralCode> useReferralCode(@PathVariable String codeValue, @RequestParam String userId) {
        try {
            ReferralCode usedCode = referralCodeService.useReferralCode(codeValue, userId);
            return ResponseEntity.ok(usedCode);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/expired")
    public ResponseEntity<List<ReferralCode>> getExpiredReferralCodes() {
        List<ReferralCode> expiredCodes = referralCodeService.getExpiredReferralCodes();
        return ResponseEntity.ok(expiredCodes);
    }

    @PostMapping("/cleanup")
    public ResponseEntity<Void> deactivateExpiredCodes() {
        referralCodeService.deactivateExpiredCodes();
        return ResponseEntity.ok().build();
    }
}
