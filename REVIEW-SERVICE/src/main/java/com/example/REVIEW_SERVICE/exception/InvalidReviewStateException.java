package com.example.REVIEW_SERVICE.exception;

public class InvalidReviewStateException extends RuntimeException {
    public InvalidReviewStateException(String message) {
        super(message);
    }
}
