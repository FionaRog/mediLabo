package com.medilabo.risk_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

/**
 * Global exception handler responsible for converting application exceptions
 * into appropriate HTTP responses.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions thrown when a requested patient cannot be found.
     *
     * @param e the exception containing information about the missing patient
     * @return a response with HTTP status 404 and the exception message
     */
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> handlePatientNotFoundException(PatientNotFoundException e) {

        log.warn("Patient not found during risk assessment: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    /**
     * Handles failures when a required external service cannot be reached.
     *
     * @param e the exception raised while accessing an external service
     * @return a response with HTTP status 503
     */
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleServiceUnavailable(ResourceAccessException e) {

        log.error("External service unavailable: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("A required service is unavailable");
    }
}
