package com.medilabo.patient_service.service;

import com.medilabo.patient_service.dto.PatientDto;
import com.medilabo.patient_service.exception.PatientNotFoundException;
import com.medilabo.patient_service.mapper.PatientMapper;
import com.medilabo.patient_service.model.Patient;
import com.medilabo.patient_service.model.enums.Gender;
import com.medilabo.patient_service.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for patient management.
 * Handles patient retrieval, creation and update operations.
 */
@Slf4j
@Service
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    public PatientService(PatientRepository patientRepository,  PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
    }

    /**
     * Retrieves all patients.
     *
     * @return the list of all patients
     */
    @Override
    public List<PatientDto> findAllPatients() {

        return patientRepository.findAll()
                .stream()
                .map(patientMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id the ID of the patient to retrieve
     * @return the patient matching the given ID
     * @throws PatientNotFoundException if no patient exists with the given ID
     */
    @Override
    public PatientDto findPatientById(Integer id) {
        log.debug("Searching patient with id: {}", id);

        Patient patient = patientRepository.findById(id)
                        .orElseThrow(() -> {
                            log.warn("Patient not found with id {}", id);
                            return new PatientNotFoundException(id);
                });

        return patientMapper.toDto(patient);
    }

    /**
     * Creates and saves a new patient.
     *
     * @param patientDto the patient to create
     * @return the created patient
     */
    @Override
    public PatientDto addNewPatient(PatientDto patientDto) {
        Patient patient = patientMapper.toEntity(patientDto);

        Patient newPatient = patientRepository.save(patient);

        log.info("Patient created with id {}", newPatient.getId());

        return patientMapper.toDto(newPatient);
    }

    /**
     * Updates an existing patient identified by its ID.
     *
     * @param id the ID of the patient to update
     * @param patientDto the new patient information
     * @return the updated patient
     * @throws PatientNotFoundException if no patient exists with the given ID
     */
    @Override
    public PatientDto updatePatient(Integer id, PatientDto patientDto) {
        log.debug("Updating patient with id: {}", id);

        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Patient not found with id {}", id);
                    return new PatientNotFoundException(id);
                });

        existingPatient.setFirstname(patientDto.getFirstname());
        existingPatient.setLastname(patientDto.getLastname());
        existingPatient.setDateOfBirth(patientDto.getDateOfBirth());
        existingPatient.setGender(Gender.valueOf(patientDto.getGender()));
        existingPatient.setAddress(patientDto.getAddress());
        existingPatient.setTelephone(patientDto.getTelephone());

        Patient updatedPatient = patientRepository.save(existingPatient);

        log.info("Patient updated with id {}", id);

        return patientMapper.toDto(updatedPatient);
    }


}
