package ru.lomov.flash.party.exceptions;

public class PartyNotFoundException extends RuntimeException {
    public PartyNotFoundException(String message) {
        super(message);
    }

    public PartyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
