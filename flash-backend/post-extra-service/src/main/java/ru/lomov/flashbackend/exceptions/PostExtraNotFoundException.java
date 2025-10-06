package ru.lomov.flashbackend.exceptions;

public class PostExtraNotFoundException extends RuntimeException {
    public PostExtraNotFoundException(String message) {
        super(message);
    }

    public PostExtraNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
