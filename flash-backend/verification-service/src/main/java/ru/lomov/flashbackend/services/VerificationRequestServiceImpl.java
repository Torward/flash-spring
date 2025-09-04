package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.VerificationRequest;
import ru.lomov.flashbackend.repositories.VerificationRequestRepository;
import ru.lomov.flashbackend.exceptions.VerificationRequestNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationRequestServiceImpl implements VerificationRequestService {

    private final VerificationRequestRepository verificationRequestRepository;

    @Override
    public VerificationRequest createVerificationRequest(VerificationRequest request) {
        if (request.getRequestId() == null) {
            request.setRequestId(UUID.randomUUID().toString());
        }
        return verificationRequestRepository.save(request);
    }

    @Override
    public Optional<VerificationRequest> getVerificationRequestById(String id) {
        return verificationRequestRepository.findById(id);
    }

    @Override
    public List<VerificationRequest> getAllVerificationRequests() {
        return verificationRequestRepository.findAll();
    }

    @Override
    public List<VerificationRequest> getVerificationRequestsByUserId(String userId) {
        return verificationRequestRepository.findByUserId(userId);
    }

    @Override
    public List<VerificationRequest> getVerificationRequestsByStatus(String status) {
        return verificationRequestRepository.findByStatus(status);
    }

    @Override
    public VerificationRequest updateVerificationRequest(String id, VerificationRequest request) {
        VerificationRequest existingRequest = verificationRequestRepository.findById(id)
            .orElseThrow(() -> new VerificationRequestNotFoundException("Verification request not found with id: " + id));

        // Update fields
        existingRequest.setStatus(request.getStatus());
        existingRequest.setReason(request.getReason());

        return verificationRequestRepository.save(existingRequest);
    }

    @Override
    public void deleteVerificationRequest(String id) {
        VerificationRequest request = verificationRequestRepository.findById(id)
            .orElseThrow(() -> new VerificationRequestNotFoundException("Verification request not found with id: " + id));

        verificationRequestRepository.delete(request);
    }
}
