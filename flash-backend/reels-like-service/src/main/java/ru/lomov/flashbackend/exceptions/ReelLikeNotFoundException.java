package ru.lomov.flashbackend.exceptions;

public class ReelLikeNotFoundException extends RuntimeException {
    public ReelLikeNotFoundException(String message) {
        super(message);
    }

    public ReelLikeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
