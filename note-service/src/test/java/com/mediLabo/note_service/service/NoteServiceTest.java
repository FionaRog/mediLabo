package com.mediLabo.note_service.service;

import com.mediLabo.note_service.dto.NoteDto;
import com.mediLabo.note_service.mapper.NoteMapper;
import com.mediLabo.note_service.model.Note;
import com.mediLabo.note_service.repository.NoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NoteMapper noteMapper;

    private INoteService noteService;

    @BeforeEach
    public void setUp() {
        noteService = new NoteService(noteRepository, noteMapper);
    }

    @Test
    @DisplayName("Should return Notes by Patient's ID")
    void getNotesByPatientId(){
        Note note = new Note();
        note.setPatient("Test1");
        note.setNote("This is a test");
        note.setPatId(1);
        note.setId("abc");

        NoteDto noteDto = new NoteDto();
        noteDto.setNote("This is a test");
        noteDto.setPatId(1);
        noteDto.setPatient("Test1");
        noteDto.setId("abc");

        when(noteRepository.findByPatId(1)).thenReturn(List.of(note));
        when(noteMapper.toDto(note)).thenReturn(noteDto);

        List<NoteDto> result = noteService.getNotesByPatientId(note.getPatId());

        assertEquals(note.getNote(), result.get(0).getNote());
        assertThat(result).hasSize(1);

        verify(noteRepository).findByPatId(1);
        verify(noteMapper).toDto(note);
    }

    @Test
    @DisplayName("Should create note")
    void createNoteTest(){
        NoteDto noteDto = new NoteDto();
        noteDto.setNote("This is a test");
        noteDto.setPatId(1);
        noteDto.setPatient("Test");
        noteDto.setId("abc");

        Note note = new Note();
        note.setNote("This is a test");
        note.setPatId(1);
        note.setId("abc");
        note.setPatient("Test");

        when(noteMapper.toEntity(noteDto)).thenReturn(note);
        when(noteMapper.toDto(note)).thenReturn(noteDto);
        when(noteRepository.insert(note)).thenReturn(note);

        NoteDto createdNote = noteService.createNote(noteDto);

        assertEquals(noteDto.getPatient(), createdNote.getPatient());
        verify(noteRepository).insert(note);
        verify(noteMapper).toDto(note);
        verify(noteMapper).toEntity(noteDto);
    }


}
