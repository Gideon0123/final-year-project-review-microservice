package com.example.REVIEW_SERVICE.exception;

public class ReviewerNotFoundException extends RuntimeException {
    public ReviewerNotFoundException(String message) {
        super(message);
    }
}
