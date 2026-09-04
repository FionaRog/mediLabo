package com.medilabo.patient_service.mapper;

import com.medilabo.patient_service.dto.PatientDto;
import com.medilabo.patient_service.model.Patient;
import com.medilabo.patient_service.model.enums.Gender;
import org.springframework.stereotype.Component;

/**
 * Maps patient entities to DTOs and DTOs to patient entities.
 */
@Component
public class PatientMapper {

    /**
     * Converts a patient entity to a patient DTO.
     *
     * @param patient the patient entity to convert
     * @return the corresponding patient DTO
     */
    public PatientDto toDto(Patient patient) {
        PatientDto patientDto = new PatientDto();

        patientDto.setId(patient.getId());
        patientDto.setFirstname(patient.getFirstname());
        patientDto.setLastname(patient.getLastname());
        patientDto.setDateOfBirth(patient.getDateOfBirth());
        patientDto.setGender(patient.getGender().name());
        patientDto.setAddress(patient.getAddress());
        patientDto.setTelephone(patient.getTelephone());

        return patientDto;
    }

    /**
     * Converts a patient DTO to a patient entity.
     *
     * @param patientDto the patient DTO to convert
     * @return the corresponding patient entity
     */
    public Patient toEntity(PatientDto patientDto) {
        Patient patient = new Patient();

        patient.setId(patientDto.getId());
        patient.setFirstname(patientDto.getFirstname());
        patient.setLastname(patientDto.getLastname());
        patient.setDateOfBirth(patientDto.getDateOfBirth());
        patient.setGender(Gender.valueOf(patientDto.getGender()));
        patient.setAddress(patientDto.getAddress());
        patient.setTelephone(patientDto.getTelephone());

        return patient;
    }
}