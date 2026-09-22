package com.medilabo.risk_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data transfer object representing the patient information
 * required for diabetes risk assessment.
 */
@Getter
@Setter
public class PatientDto {

    private Integer id;
    private LocalDate dateOfBirth;
    private String gender;
}
