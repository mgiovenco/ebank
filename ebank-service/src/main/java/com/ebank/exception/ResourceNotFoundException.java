package com.ebank.exception;

/**
 * Thrown when the fetch of a resource has no result
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String s) {
        super(s);
    }
}