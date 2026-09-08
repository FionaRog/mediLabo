package com.mediLabo.note_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object used to transfer medical note data between
 * the API layer and the service layer.
 * <p>
 * Validation constraints ensure that a note is associated with a patient
 * and contains the required information.
 * </p>
 */
@Getter
@Setter
public class NoteDto {

    @NotBlank(message = "Note is required")
    private String note;

    @NotNull(message = "Patient id is required")
    private Integer patId;

    @NotBlank(message = "Patient name is required")
    private String patient;

}
