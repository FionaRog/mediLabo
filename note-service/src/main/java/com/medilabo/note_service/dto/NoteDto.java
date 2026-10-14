package com.medilabo.note_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object used to transfer medical note data between
 * the API layer and the service layer.
 * <p>
 * Validation constraints require a patient identifier and non-blank note text.
 * </p>
 */
@Getter
@Setter
public class NoteDto {

    private String id;

    @NotBlank(message = "Note is required")
    private String note;

    @NotNull(message = "Patient id is required")
    private Integer patId;

    private LocalDateTime createdAt;


}
