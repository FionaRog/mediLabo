package com.mediLabo.note_service.controller;

import com.mediLabo.note_service.dto.NoteDto;
import com.mediLabo.note_service.model.Note;
import com.mediLabo.note_service.service.INoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private INoteService noteService;

    @Test
    @DisplayName("Should display notes when patient's ID is given")
    public void getNoteById() throws Exception {
        NoteDto noteDto = new NoteDto();
        noteDto.setPatient("Test");
        noteDto.setPatId(1);
        noteDto.setNote("A first note");
        noteDto.setId("abc");

        NoteDto noteDto2 = new NoteDto();
        noteDto2.setPatient("Test");
        noteDto2.setPatId(1);
        noteDto2.setNote("A second note");
        noteDto2.setId("abcd");

        when(noteService.getNotesByPatientId(1)).thenReturn(Arrays.asList(noteDto, noteDto2));

        mockMvc.perform(get("/notes/patient/{patId}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].patId").value(1))
                .andExpect(jsonPath("$[0].note").value("A first note"))
                .andExpect(jsonPath("$[1].note").value("A second note"))
                .andExpect(jsonPath("$[0].patient").value("Test"))
                .andExpect(jsonPath("$.length()").value(2));

        verify(noteService).getNotesByPatientId(1);
    }

    @Test
    @DisplayName("Should return note when a note is created")
    public void createNoteTest () throws Exception {
        NoteDto noteDto = new NoteDto();
        noteDto.setPatient("Test");
        noteDto.setPatId(1);
        noteDto.setNote("A first note");

        when(noteService.createNote(any(NoteDto.class))).thenReturn(noteDto);

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.patient").value("Test"))
                .andExpect(jsonPath("$.note").value("A first note"))
                .andExpect(jsonPath("$.patId").value(1));

        verify(noteService).createNote(any(NoteDto.class));
    }

    @Test
    @DisplayName("Should return bad request when note is invalid")
    void createNoteWithInvalidDataTest() throws Exception {

        NoteDto noteDto = new NoteDto();

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(noteDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.patId").value("Patient id is required"))
                .andExpect(jsonPath("$.patient").value("Patient name is required"))
                .andExpect(jsonPath("$.note").value("Note is required"));

        verifyNoInteractions(noteService);
    }
}
