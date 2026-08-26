package com.medilabo.patient_service.exception;

public class PatientNotFoundException extends RuntimeException {

    public  PatientNotFoundException(Integer id) {
        super("Patient not found with id " + id);
    }
}
