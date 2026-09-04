package com.medilabo.patient_service.service;

import com.medilabo.patient_service.dto.PatientDto;
import com.medilabo.patient_service.model.Patient;

import java.util.List;

/**
 * Defines operations for managing patients.
 */
public interface IPatientService {

    /**
     * Creates and saves a new patient.
     *
     * @param patientDto the patient data to create
     * @return the created patient
     */
    PatientDto addNewPatient(PatientDto patientDto);

    /**
     * Updates an existing patient identified by its ID.
     *
     * @param id the ID of the patient to update
     * @param patientDto the updated patient information
     * @return the updated patient
     */
    PatientDto updatePatient(Integer id, PatientDto patientDto);

    /**
     * Retrieves a patient by ID.
     *
     * @param id the ID of the patient to retrieve
     * @return the patient matching the given ID
     */
    PatientDto findPatientById(Integer id);

    /**
     * Retrieves all patients.
     *
     * @return the list of all patients
     */
    List<PatientDto> findAllPatients();
}
