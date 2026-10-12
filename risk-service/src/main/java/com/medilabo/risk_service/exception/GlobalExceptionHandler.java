package com.medilabo.risk_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;

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
     * @return a response with HTTP status 404 and a JSON body containing the error message
     */
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlePatientNotFoundException(
            PatientNotFoundException e) {

        log.warn("Patient not found during risk assessment: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", e.getMessage()));
    }

    /**
     * Handles failures when a required external service cannot be reached.
     *
     * @param e the exception raised while accessing an external service
     * @return a response with HTTP status 503 and a JSON body describing service unavailability
     */
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Map<String, String>> handleServiceUnavailable(
            ResourceAccessException e) {

        log.error("External service unavailable: {}", e.getMessage());

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of("error", "A required service is unavailable"));
    }

    /**
     * Handles HTTP error responses returned by a required service.
     *
     * @param e the exception containing the upstream HTTP error
     * @return a response with HTTP status 502 and a JSON body describing an upstream service error
     */
    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<Map<String, String>> handleUpstreamHttpError(
            RestClientResponseException e) {

        log.error(
                "Required service returned HTTP status {}",
                e.getStatusCode().value()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(Map.of("error", "A required service returned an error"));
    }
}
