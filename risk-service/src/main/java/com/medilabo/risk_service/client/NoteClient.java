package com.medilabo.risk_service.client;

import com.medilabo.risk_service.dto.NoteDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * HTTP client responsible for communication with the note service.
 * <p>
 * Retrieves the medical notes associated with a patient
 * from the configured note service.
 * </p>
 */
@Slf4j
@Component
public class NoteClient {

    private final RestClient restClient;

    /**
     * Creates a client configured with the base URL of the note service.
     *
     * @param builder        the builder used to create the REST client
     * @param noteServiceUrl the base URL of the note service
     */
    public NoteClient(RestClient.Builder builder,
                      @Value("${note-service.url}") String noteServiceUrl) {

        this.restClient = builder
                .baseUrl(noteServiceUrl)
                .build();
    }

    /**
     * Retrieves all medical notes associated with the specified patient.
     *
     * @param patientId the identifier of the patient
     * @return the list of notes associated with the patient
     */
    public List<NoteDto> getNotesByPatientId(Integer patientId) {

        log.debug("Requesting notes for patient id {}", patientId);

        return restClient
                .get()
                .uri("/notes/patient/{patientId}", patientId)
                .retrieve()
                .body(new ParameterizedTypeReference<List<NoteDto>>() {});
    }
}
