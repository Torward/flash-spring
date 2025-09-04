package ru.lomov.flashbackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.entities.VerificationRequest;
import ru.lomov.flashbackend.services.VerificationRequestService;

import java.util.List;

@RestController
@RequestMapping("/verification-requests")
@RequiredArgsConstructor
@Tag(name = "Verification Requests", description = "API for managing user verification requests")
public class VerificationRequestController {

    private final VerificationRequestService verificationRequestService;

    @PostMapping
    @Operation(summary = "Create a new verification request")
    public ResponseEntity<VerificationRequest> createVerificationRequest(@RequestBody VerificationRequest request) {
        VerificationRequest createdRequest = verificationRequestService.createVerificationRequest(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get verification request by ID")
    public ResponseEntity<VerificationRequest> getVerificationRequestById(@PathVariable String id) {
        return verificationRequestService.getVerificationRequestById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all verification requests")
    public ResponseEntity<List<VerificationRequest>> getAllVerificationRequests() {
        List<VerificationRequest> requests = verificationRequestService.getAllVerificationRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get verification requests by user ID")
    public ResponseEntity<List<VerificationRequest>> getVerificationRequestsByUserId(@PathVariable String userId) {
        List<VerificationRequest> requests = verificationRequestService.getVerificationRequestsByUserId(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get verification requests by status")
    public ResponseEntity<List<VerificationRequest>> getVerificationRequestsByStatus(@PathVariable String status) {
        List<VerificationRequest> requests = verificationRequestService.getVerificationRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update verification request")
    public ResponseEntity<VerificationRequest> updateVerificationRequest(@PathVariable String id, @RequestBody VerificationRequest request) {
        VerificationRequest updatedRequest = verificationRequestService.updateVerificationRequest(id, request);
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete verification request")
    public ResponseEntity<Void> deleteVerificationRequest(@PathVariable String id) {
        verificationRequestService.deleteVerificationRequest(id);
        return ResponseEntity.noContent().build();
    }
}
