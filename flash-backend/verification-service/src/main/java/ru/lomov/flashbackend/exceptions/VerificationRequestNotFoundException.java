package ru.lomov.flashbackend.exceptions;

public class VerificationRequestNotFoundException extends RuntimeException {
    public VerificationRequestNotFoundException(String message) {
        super(message);
    }

    public VerificationRequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
