package ru.lomov.flashbackend.exceptions;

public class SavedReelNotFoundException extends RuntimeException {
    public SavedReelNotFoundException(String message) {
        super(message);
    }

    public SavedReelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
