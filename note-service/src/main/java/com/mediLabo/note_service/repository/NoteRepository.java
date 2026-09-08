package com.mediLabo.note_service.repository;

import com.mediLabo.note_service.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository providing MongoDB persistence operations for {@link Note} documents.
 */
public interface NoteRepository extends MongoRepository<Note, String> {

    /**
     * Retrieves all medical notes associated with the specified patient identifier.
     *
     * @param patId the identifier of the patient
     * @return the list of notes associated with the patient,
     *         or an empty list if no note is found
     */
    List<Note> findByPatId(Integer patId);
}
