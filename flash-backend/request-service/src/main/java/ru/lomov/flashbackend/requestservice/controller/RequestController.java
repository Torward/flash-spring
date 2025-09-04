package ru.lomov.flashbackend.requestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.requestservice.dto.ApiResponse;
import ru.lomov.flashbackend.requestservice.dto.CreateRequestDto;
import ru.lomov.flashbackend.requestservice.dto.RequestDto;
import ru.lomov.flashbackend.requestservice.dto.UpdateRequestDto;
import ru.lomov.flashbackend.requestservice.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<RequestDto> createRequest(@RequestBody CreateRequestDto createRequestDto) {
        RequestDto requestDto = requestService.createRequest(createRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestDto);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<RequestDto> getRequest(@PathVariable String requestId) {
        RequestDto requestDto = requestService.getRequestById(requestId);
        return ResponseEntity.ok(requestDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RequestDto>> getUserRequests(@PathVariable String userId) {
        List<RequestDto> requests = requestService.getRequestsByUserId(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RequestDto>> getRequestsByStatus(@PathVariable String status) {
        List<RequestDto> requests = requestService.getRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }

    @GetMapping
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        List<RequestDto> requests = requestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{requestId}/approve")
    public ResponseEntity<RequestDto> approveRequest(
            @PathVariable String requestId,
            @RequestBody UpdateRequestDto updateRequestDto) {
        updateRequestDto.setStatus("APPROVED");
        RequestDto requestDto = requestService.updateRequest(requestId, updateRequestDto);
        return ResponseEntity.ok(requestDto);
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<RequestDto> rejectRequest(
            @PathVariable String requestId,
            @RequestBody UpdateRequestDto updateRequestDto) {
        updateRequestDto.setStatus("REJECTED");
        RequestDto requestDto = requestService.updateRequest(requestId, updateRequestDto);
        return ResponseEntity.ok(requestDto);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<RequestDto> updateRequest(
            @PathVariable String requestId,
            @RequestBody UpdateRequestDto updateRequestDto) {
        RequestDto requestDto = requestService.updateRequest(requestId, updateRequestDto);
        return ResponseEntity.ok(requestDto);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<ApiResponse> deleteRequest(@PathVariable String requestId) {
        requestService.deleteRequest(requestId);
        return ResponseEntity.ok(new ApiResponse("success", "Request deleted successfully"));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<RequestDto>> getPendingRequests() {
        List<RequestDto> requests = requestService.getRequestsByStatus("PENDING");
        return ResponseEntity.ok(requests);
    }
}
