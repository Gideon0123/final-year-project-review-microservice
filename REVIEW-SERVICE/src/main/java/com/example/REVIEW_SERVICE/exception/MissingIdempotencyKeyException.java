package com.example.REVIEW_SERVICE.exception;

public class MissingIdempotencyKeyException extends RuntimeException {
    public MissingIdempotencyKeyException(String message) {
        super(message);
    }
}
