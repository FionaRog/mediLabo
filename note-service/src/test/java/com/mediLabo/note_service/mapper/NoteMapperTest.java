package com.mediLabo.note_service.mapper;

import com.mediLabo.note_service.dto.NoteDto;
import com.mediLabo.note_service.model.Note;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NoteMapperTest {

    private final NoteMapper noteMapper = new NoteMapper();

    @Test
    @DisplayName("Should map NoteDto into Note")
    void noteDtoToNoteTest() {
        NoteDto noteDto = new NoteDto();
        noteDto.setPatient("Test");
        noteDto.setNote("This is a test");
        noteDto.setPatId(1);
        noteDto.setId("abc");
        noteDto.setCreatedAt(LocalDateTime.now());

        Note toNote = noteMapper.toEntity(noteDto);

        assertEquals(noteDto.getPatient(), toNote.getPatient());
        assertEquals(noteDto.getNote(), toNote.getNote());
        assertEquals(noteDto.getPatId(), toNote.getPatId());

        assertNull(toNote.getId());
        assertNull(toNote.getCreatedAt());
    }

    @Test
    @DisplayName("Should map Note into NoteDto")
    void noteToNoteDtoTest() {
        Note note = new Note();
        note.setPatient("Test");
        note.setNote("This is a test");
        note.setPatId(1);
        note.setId("abc");
        note.setCreatedAt(LocalDateTime.now());

        NoteDto toNoteDto = noteMapper.toDto(note);

        assertEquals(note.getPatient(), toNoteDto.getPatient());
        assertEquals(note.getNote(), toNoteDto.getNote());
        assertEquals(note.getPatId(), toNoteDto.getPatId());
        assertEquals(note.getId(), toNoteDto.getId());
        assertEquals(note.getCreatedAt(), toNoteDto.getCreatedAt());
    }
}
