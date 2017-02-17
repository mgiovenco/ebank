package com.ebank.web;

import com.ebank.exception.ResourceNotFoundException;
import com.ebank.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class SpringErrorHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    String handleResourceNotFoundException(HttpServletRequest req, ResourceNotFoundException ex) {
        return ex.getLocalizedMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    String handleValidationException(HttpServletRequest req, ValidationException ex) {
        return ex.getLocalizedMessage();
    }
}
