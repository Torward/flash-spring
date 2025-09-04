package ru.lomov.flash.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.request.entity.Request;
import ru.lomov.flash.request.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Override
    public Request createRequest(Request request) {
        request.setStatus("PENDING");
        request.setCreatedAt(LocalDateTime.now());
        return requestRepository.save(request);
    }

    @Override
    public Optional<Request> getRequestById(String requestId) {
        return requestRepository.findById(requestId);
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public List<Request> getRequestsByUserId(String userId) {
        return requestRepository.findByUserId(userId);
    }

    @Override
    public List<Request> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(status);
    }

    @Override
    public List<Request> getRequestsByType(String type) {
        return requestRepository.findByType(type);
    }

    @Override
    public Request updateRequest(String requestId, Request request) {
        return requestRepository.findById(requestId)
                .map(existingRequest -> {
                    existingRequest.setType(request.getType());
                    existingRequest.setAmount(request.getAmount());
                    existingRequest.setStatus(request.getStatus());
                    existingRequest.setProcessedAt(request.getProcessedAt());
                    return requestRepository.save(existingRequest);
                })
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    @Override
    public void deleteRequest(String requestId) {
        requestRepository.deleteById(requestId);
    }

    @Override
    public Request processRequest(String requestId) {
        return requestRepository.findById(requestId)
                .map(request -> {
                    request.setStatus("PROCESSED");
                    request.setProcessedAt(LocalDateTime.now());
                    return requestRepository.save(request);
                })
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }
}
