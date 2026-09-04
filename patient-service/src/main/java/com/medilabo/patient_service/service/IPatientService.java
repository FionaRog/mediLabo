package com.medilabo.patient_service.service;

import com.medilabo.patient_service.model.Patient;

import java.util.List;

/**
 * Defines operations for managing patients.
 */
public interface IPatientService {

    /**
     * Creates and saves a new patient.
     *
     * @param patient the patient to create
     * @return the created patient
     */
    Patient addNewPatient(Patient patient);

    /**
     * Updates an existing patient identified by its ID.
     *
     * @param id the ID of the patient to update
     * @param patient the new patient information
     * @return the updated patient
     */
    Patient updatePatient(Integer id, Patient patient);

    /**
     * Retrieves a patient by ID.
     *
     * @param id the ID of the patient to retrieve
     * @return the patient matching the given ID
     */
    Patient findPatientById(Integer id);

    /**
     * Retrieves all patients.
     *
     * @return the list of all patients
     */
    List<Patient> findAllPatients();
}
