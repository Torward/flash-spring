package ru.lomov.flash.request.service;

import ru.lomov.flash.request.entity.Request;

import java.util.List;
import java.util.Optional;

public interface RequestService {
    Request createRequest(Request request);
    Optional<Request> getRequestById(String requestId);
    List<Request> getAllRequests();
    List<Request> getRequestsByUserId(String userId);
    List<Request> getRequestsByStatus(String status);
    List<Request> getRequestsByType(String type);
    Request updateRequest(String requestId, Request request);
    void deleteRequest(String requestId);
    Request processRequest(String requestId);
}
