package ru.lomov.flashbackend.exceptions;

public class ReelViewNotFoundException extends RuntimeException {
    public ReelViewNotFoundException(String message) {
        super(message);
    }

    public ReelViewNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
