package com.medilabo.patient_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Data Transfer Object used to exchange patient data through the API.
 * It prevents the persistence model from being exposed directly.
 */
@Getter
@Setter
public class PatientDto {

    private Integer id;

    @NotBlank(message = "First Name is required")
    private String firstname;

    @NotBlank(message = "Last Name is required")
    private String lastname;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    @Pattern(regexp = "M|F", message = "Gender must be M or F")
    private String gender;

    private String address;

    private String telephone;
}
