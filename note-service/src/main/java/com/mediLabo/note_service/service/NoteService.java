package com.mediLabo.note_service.service;

import com.mediLabo.note_service.dto.NoteDto;
import com.mediLabo.note_service.mapper.NoteMapper;
import com.mediLabo.note_service.model.Note;
import com.mediLabo.note_service.repository.NoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementing the business operations used to manage patient notes.
 */
@Service
@Slf4j
public class NoteService implements INoteService {

    private NoteRepository noteRepository;

    private NoteMapper noteMapper;

    public NoteService(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    @Override
    public List<NoteDto> getNotesByPatientId(Integer patId) {
        log.debug("Retrieving notes for patient with id {}", patId);

        List<NoteDto> noteDtos = noteRepository.findByPatId(patId)
                .stream()
                .map(noteMapper::toDto)
                .toList();

        log.debug("{} note(s) found for patient with id {}", noteDtos.size(), patId);

        return noteDtos;
    }

    @Override
    public NoteDto createNote(NoteDto noteDto) {
        log.debug("Creating note for patient with id {}", noteDto.getPatId());

        Note note = noteMapper.toEntity(noteDto);

        Note savedNote = noteRepository.insert(note);

        log.info("Note created for patient with id {}", savedNote.getPatId());

        return noteMapper.toDto(savedNote);
    }

}
