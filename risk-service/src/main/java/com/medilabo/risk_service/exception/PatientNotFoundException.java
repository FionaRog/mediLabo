package com.medilabo.risk_service.exception;

/**
 * Exception thrown when a patient cannot be found.
 */
public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(Integer patientId) {
        super("Patient not found with id " + patientId);
    }
}
