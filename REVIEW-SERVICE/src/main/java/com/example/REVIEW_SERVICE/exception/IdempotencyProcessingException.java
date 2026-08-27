package com.example.REVIEW_SERVICE.exception;

public class IdempotencyProcessingException extends RuntimeException {
  public IdempotencyProcessingException(String message) {
    super(message);
  }
}
