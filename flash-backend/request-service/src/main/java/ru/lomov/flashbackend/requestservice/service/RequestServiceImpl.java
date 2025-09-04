package ru.lomov.flashbackend.requestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flashbackend.requestservice.dto.CreateRequestDto;
import ru.lomov.flashbackend.requestservice.dto.RequestDto;
import ru.lomov.flashbackend.requestservice.dto.UpdateRequestDto;
import ru.lomov.flashbackend.requestservice.entity.Request;
import ru.lomov.flashbackend.requestservice.exception.RequestNotFoundException;
import ru.lomov.flashbackend.requestservice.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Override
    public RequestDto createRequest(CreateRequestDto createRequestDto) {
        Request request = new Request();
        request.setUserId(createRequestDto.getUserId());
        request.setAmount(createRequestDto.getAmount());
        request.setPaymentMethod(createRequestDto.getPaymentMethod());
        request.setAccountDetails(createRequestDto.getAccountDetails());
        request.setCurrency(createRequestDto.getCurrency());
        request.setStatus(Request.STATUS_PENDING);

        Request savedRequest = requestRepository.save(request);
        return convertToDto(savedRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public RequestDto getRequestById(String requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Request not found with id: " + requestId));
        return convertToDto(request);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDto> getRequestsByUserId(String userId) {
        return requestRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDto> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(status)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDto> getAllRequests() {
        return requestRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public RequestDto updateRequest(String requestId, UpdateRequestDto updateRequestDto) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Request not found with id: " + requestId));

        if (updateRequestDto.getStatus() != null) {
            request.setStatus(updateRequestDto.getStatus());
            if (Request.STATUS_APPROVED.equals(updateRequestDto.getStatus()) ||
                Request.STATUS_REJECTED.equals(updateRequestDto.getStatus())) {
                request.setProcessedAt(LocalDateTime.now());
                request.setProcessedBy(updateRequestDto.getProcessedBy());
            }
        }

        if (updateRequestDto.getRejectionReason() != null) {
            request.setRejectionReason(updateRequestDto.getRejectionReason());
        }

        if (updateRequestDto.getTransactionId() != null) {
            request.setTransactionId(updateRequestDto.getTransactionId());
        }

        if (updateRequestDto.getFeeAmount() != null) {
            request.setFeeAmount(updateRequestDto.getFeeAmount());
        }

        Request updatedRequest = requestRepository.save(request);
        return convertToDto(updatedRequest);
    }

    @Override
    public void deleteRequest(String requestId) {
        if (!requestRepository.existsById(requestId)) {
            throw new RequestNotFoundException("Request not found with id: " + requestId);
        }
        requestRepository.deleteById(requestId);
    }

    private RequestDto convertToDto(Request request) {
        return RequestDto.builder()
                .requestId(request.getRequestId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .status(request.getStatus())
                .paymentMethod(request.getPaymentMethod())
                .accountDetails(request.getAccountDetails())
                .currency(request.getCurrency())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .processedAt(request.getProcessedAt())
                .processedBy(request.getProcessedBy())
                .rejectionReason(request.getRejectionReason())
                .transactionId(request.getTransactionId())
                .feeAmount(request.getFeeAmount())
                .netAmount(request.getNetAmount())
                .build();
    }
}
