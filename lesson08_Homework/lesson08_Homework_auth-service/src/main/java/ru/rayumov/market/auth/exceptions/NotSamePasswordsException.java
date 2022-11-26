package ru.rayumov.market.auth.exceptions;

public class NotSamePasswordsException extends RuntimeException {

    public NotSamePasswordsException(String message) {
        super(message);
    }
}

