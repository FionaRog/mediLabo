package com.mediLabo.note_service.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Represents a medical note associated with a patient.
 * <p>
 * Each instance is stored as a document in the MongoDB {@code notes} collection.
 * A patient can be associated with multiple notes through the same patient identifier.
 * </p>
 */
@Getter
@Setter
@Document(collection = "notes")
public class Note {

    @Id
    private String id;

    private String note;

    private Integer patId;

    private String patient;

    private LocalDateTime createdAt;

}
