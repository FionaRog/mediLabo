package com.medilabo.patient_service.service;

import com.medilabo.patient_service.exception.PatientNotFoundException;
import com.medilabo.patient_service.model.Patient;
import com.medilabo.patient_service.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service implementation for patient management.
 * Handles patient retrieval, creation and update operations.
 */
@Slf4j
@Service
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id the ID of the patient to retrieve
     * @return the patient matching the given ID
     * @throws PatientNotFoundException if no patient exists with the given ID
     */
    @Override
    public Patient findPatientById(Integer id) {
        log.debug("Searching patient with id: {}", id);

        return patientRepository.findById(id)
                        .orElseThrow(() -> {
                            log.warn("Patient not found with id {}", id);
                            return new PatientNotFoundException(id);
                });
    }

    /**
     * Creates and saves a new patient.
     *
     * @param patient the patient to create
     * @return the created patient
     */
    @Override
    public Patient addNewPatient(Patient patient) {
        Patient newPatient = patientRepository.save(patient);

        log.info("Patient created with id {}", newPatient.getId());

        return newPatient;
    }

    /**
     * Updates an existing patient identified by its ID.
     *
     * @param id the ID of the patient to update
     * @param patient the new patient information
     * @return the updated patient
     * @throws PatientNotFoundException if no patient exists with the given ID
     */
    @Override
    public Patient updatePatient(Integer id, Patient patient) {
        log.debug("Updating patient with id: {}", id);

        Patient existingPatient = findPatientById(id);

        existingPatient.setFirstname(patient.getFirstname());
        existingPatient.setLastname(patient.getLastname());
        existingPatient.setDateOfBirth(patient.getDateOfBirth());
        existingPatient.setGender(patient.getGender());
        existingPatient.setAddress(patient.getAddress());
        existingPatient.setTelephone(patient.getTelephone());

        Patient updatedPatient = patientRepository.save(existingPatient);

        log.info("Patient updated with id {}", id);

        return updatedPatient;
    }


}
