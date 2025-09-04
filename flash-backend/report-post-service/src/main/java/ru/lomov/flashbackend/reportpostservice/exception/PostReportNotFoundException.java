package ru.lomov.flashbackend.reportpostservice.exception;

public class PostReportNotFoundException extends RuntimeException {
    public PostReportNotFoundException(String message) {
        super(message);
    }
}
