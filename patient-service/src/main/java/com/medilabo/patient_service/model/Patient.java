package com.medilabo.patient_service.model;

import com.medilabo.patient_service.model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "First Name is required")
    private String firstname;

    @NotBlank(message = "Last Name is required")
    private String lastname;

    @NotNull(message = "Birth Date is required")
    @Past(message = "Birth Date must be in the past")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender is required")
    private Gender gender;

    private String address;

    private String telephone;
}
