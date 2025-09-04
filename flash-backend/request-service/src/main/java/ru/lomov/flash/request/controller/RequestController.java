package ru.lomov.flash.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.request.entity.Request;
import ru.lomov.flash.request.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        Request createdRequest = requestService.createRequest(request);
        return ResponseEntity.ok(createdRequest);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Request> getRequestById(@PathVariable String requestId) {
        return requestService.getRequestById(requestId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        List<Request> requests = requestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Request>> getRequestsByUserId(@PathVariable String userId) {
        List<Request> requests = requestService.getRequestsByUserId(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Request>> getRequestsByStatus(@PathVariable String status) {
        List<Request> requests = requestService.getRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Request>> getRequestsByType(@PathVariable String type) {
        List<Request> requests = requestService.getRequestsByType(type);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<Request> updateRequest(@PathVariable String requestId, @RequestBody Request request) {
        try {
            Request updatedRequest = requestService.updateRequest(requestId, request);
            return ResponseEntity.ok(updatedRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteRequest(@PathVariable String requestId) {
        try {
            requestService.deleteRequest(requestId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{requestId}/process")
    public ResponseEntity<Request> processRequest(@PathVariable String requestId) {
        try {
            Request processedRequest = requestService.processRequest(requestId);
            return ResponseEntity.ok(processedRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
