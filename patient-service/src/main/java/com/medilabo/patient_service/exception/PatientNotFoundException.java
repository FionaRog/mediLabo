package com.medilabo.patient_service.exception;

/**
 * Exception thrown when a patient cannot be found
 * for the requested ID.
 */
public class PatientNotFoundException extends RuntimeException {

    /**
     * Creates an exception for the specified patient ID.
     *
     * @param id the ID of the patient that could not be found
     */
    public  PatientNotFoundException(Integer id) {
        super("Patient not found with id " + id);
    }
}
