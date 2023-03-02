package ru.otus.exception;

public class StatsErrorException extends RuntimeException {
    public StatsErrorException(String message) {
        super(message);
    }
}
