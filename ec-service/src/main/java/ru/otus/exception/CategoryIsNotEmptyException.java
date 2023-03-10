package ru.otus.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class CategoryIsNotEmptyException extends ApiError {
    public CategoryIsNotEmptyException(LocalDateTime timestamp) {
        this.status = HttpStatus.CONFLICT;
        this.reason = "For the requested operation the conditions are not met";
        this.message = "The category is not empty";
        this.timestamp = timestamp.withNano(0);
    }
}
