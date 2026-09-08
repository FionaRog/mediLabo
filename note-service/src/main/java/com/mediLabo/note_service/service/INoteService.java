package com.mediLabo.note_service.service;

import com.mediLabo.note_service.dto.NoteDto;

import java.util.List;

/**
 * Defines the operations available for managing patient notes.
 */
public interface INoteService {

    /**
     * Retrieves all notes associated with a patient.
     *
     * @param patId the patient identifier
     * @return the list of notes associated with the patient,
     *         or an empty list if none exist
     */
    List<NoteDto> getNotesByPatientId(Integer patId);

    /**
     * Creates a new patient note.
     *
     * @param noteDto the note data to create
     * @return the created note
     */
    NoteDto createNote(NoteDto noteDto);

}
