package com.medilabo.risk_service.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object representing the medical note information
 * required for diabetes risk assessment.
 */
@Getter
@Setter
public class NoteDto {

    private String note;
}
