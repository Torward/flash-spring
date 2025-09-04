package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.VerificationRequest;
import java.util.List;
import java.util.Optional;

public interface VerificationRequestService {
    VerificationRequest createVerificationRequest(VerificationRequest request);
    Optional<VerificationRequest> getVerificationRequestById(String id);
    List<VerificationRequest> getAllVerificationRequests();
    List<VerificationRequest> getVerificationRequestsByUserId(String userId);
    List<VerificationRequest> getVerificationRequestsByStatus(String status);
    VerificationRequest updateVerificationRequest(String id, VerificationRequest request);
    void deleteVerificationRequest(String id);
}
