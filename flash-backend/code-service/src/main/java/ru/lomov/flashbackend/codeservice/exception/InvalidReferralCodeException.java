package ru.lomov.flashbackend.codeservice.exception;

public class InvalidReferralCodeException extends RuntimeException {
    public InvalidReferralCodeException(String message) {
        super(message);
    }
}
