package com.ebank.exception;

/**
 * Thrown when the service is unavailable.
 */
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException(String s) {
        super(s);
    }
}
