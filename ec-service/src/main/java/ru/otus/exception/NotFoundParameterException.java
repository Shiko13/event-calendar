package ru.otus.exception;

public class NotFoundParameterException extends RuntimeException {
    public NotFoundParameterException(String message) {
        super(message);
    }
}
