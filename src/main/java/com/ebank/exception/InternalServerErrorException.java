package com.ebank.exception;

/**
 * Thrown when an internal server error has occured.
 */
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String s) {
        super(s);
    }
}