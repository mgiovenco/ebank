package com.ebank.exception;

/**
 * Thrown when a different validation exception occurs.
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String s) {
        super(s);
    }
}