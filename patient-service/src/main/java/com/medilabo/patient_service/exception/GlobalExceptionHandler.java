package com.medilabo.patient_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles exceptions raised by the patient service REST API
 * and converts them into appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles requests for a patient that does not exist.
     *
     * @param e the exception raised when the patient is not found
     * @return a response containing the error message with HTTP status 404
     */
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<String> handlePatientNotFoundException(PatientNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    /**
     * Handles validation errors on patient fields.
     *
     * @param e the exception containing the validation errors
     * @return a response containing validation errors with HTTP status 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    /**
     * Handles request bodies that cannot be deserialized.
     *
     * @param e the exception raised when the request body cannot be read
     * @return a response containing an error message with HTTP status 400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidRequestBodyException(HttpMessageNotReadableException e) {

        Map<String, String> error = new HashMap<>();

        error.put("error", "Invalid request body: check birth date and/or gender");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
