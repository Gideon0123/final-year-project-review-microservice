package com.example.REVIEW_SERVICE.exception;

public class IdempotencyConflictException extends RuntimeException {
    public IdempotencyConflictException(String message) {
        super(message);
    }
}
