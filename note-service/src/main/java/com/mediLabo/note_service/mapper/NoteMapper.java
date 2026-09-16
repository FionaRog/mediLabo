package com.mediLabo.note_service.mapper;

import com.mediLabo.note_service.dto.NoteDto;
import com.mediLabo.note_service.model.Note;
import org.springframework.stereotype.Component;

/**
 * Mapper responsible for converting between {@link Note} entities
 * and {@link NoteDto} objects.
 */
@Component
public class NoteMapper {

    /**
     * Converts a {@link Note} entity into a {@link NoteDto}.
     *
     * @param note the note entity to convert
     * @return the corresponding note DTO
     */
    public NoteDto toDto(Note note){
        NoteDto noteDto = new NoteDto();
        noteDto.setId(note.getId());
        noteDto.setNote(note.getNote());
        noteDto.setPatId(note.getPatId());
        noteDto.setPatient(note.getPatient());
        noteDto.setCreatedAt(note.getCreatedAt());
        return noteDto;
    }

    /**
     * Converts a {@link NoteDto} into a {@link Note} entity.
     *
     * @param noteDto the note DTO to convert
     * @return the corresponding note entity
     */
    public Note toEntity(NoteDto noteDto){
        Note note = new Note();
        note.setNote(noteDto.getNote());
        note.setPatient(noteDto.getPatient());
        note.setPatId(noteDto.getPatId());
        return note;
    }
}
