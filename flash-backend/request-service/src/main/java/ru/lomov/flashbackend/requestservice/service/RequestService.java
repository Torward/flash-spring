package ru.lomov.flashbackend.requestservice.service;

import ru.lomov.flashbackend.requestservice.dto.CreateRequestDto;
import ru.lomov.flashbackend.requestservice.dto.RequestDto;
import ru.lomov.flashbackend.requestservice.dto.UpdateRequestDto;

import java.util.List;

public interface RequestService {
    RequestDto createRequest(CreateRequestDto createRequestDto);
    RequestDto getRequestById(String requestId);
    List<RequestDto> getRequestsByUserId(String userId);
    List<RequestDto> getRequestsByStatus(String status);
    List<RequestDto> getAllRequests();
    RequestDto updateRequest(String requestId, UpdateRequestDto updateRequestDto);
    void deleteRequest(String requestId);
}
