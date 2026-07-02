package com.example.REVIEW_SERVICE.exception;

public class ReviewAlreadyCompletedException extends RuntimeException {
    public ReviewAlreadyCompletedException(String message) {
        super(message);
    }
}
