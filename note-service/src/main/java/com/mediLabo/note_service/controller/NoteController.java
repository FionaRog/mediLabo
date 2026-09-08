package com.mediLabo.note_service.controller;

import com.mediLabo.note_service.dto.NoteDto;
import com.mediLabo.note_service.service.INoteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing endpoints used to retrieve and create patient notes.
 */
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final INoteService noteService;

    public NoteController(INoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Retrieves all notes associated with a patient.
     *
     * @param patId the patient identifier
     * @return a response containing the patient's notes, or an empty list if none exist
     */
    @GetMapping("/patient/{patId}")
    public ResponseEntity<List<NoteDto>> getAllNotes(@PathVariable Integer patId){
        List<NoteDto> notes = noteService.getNotesByPatientId(patId);

        return ResponseEntity.ok(notes);
    }

    /**
     * Creates a new note associated with a patient.
     *
     * @param noteDto the note data to create
     * @return a response containing the created note
     */
    @PostMapping
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteDto noteDto){
        NoteDto note = noteService.createNote(noteDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(note);
    }
}
