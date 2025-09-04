package ru.lomov.flashbackend.codeservice.exception;

public class ReferralCodeNotFoundException extends RuntimeException {
    public ReferralCodeNotFoundException(String message) {
        super(message);
    }
}
