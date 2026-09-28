package com.medilabo.risk_service.client;

import com.medilabo.risk_service.dto.PatientDto;
import com.medilabo.risk_service.exception.PatientNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * HTTP client responsible for communication with the patient service.
 * <p>
 * Retrieves patient information required by the risk assessment service
 * from the configured patient service.
 * </p>
 */
@Slf4j
@Component
public class PatientClient {

    private final RestClient restClient;

    /**
     * Creates a client configured with the base URL of the patient service.
     *
     * @param builder           the builder used to create the REST client
     * @param patientServiceUrl the base URL of the patient service
     */
    public PatientClient(
            RestClient.Builder builder,
            @Value("${patient-service.url}") String patientServiceUrl) {

        this.restClient = builder
                .baseUrl(patientServiceUrl)
                .build();
    }

    /**
     * Retrieves a patient by identifier.
     *
     * @param id the identifier of the patient to retrieve
     * @return the patient information required for risk assessment
     * @throws PatientNotFoundException if the patient service returns a 404 response
     */
    public PatientDto getPatientById(Integer id) {

        log.debug("Requesting patient with id {}", id);

        return restClient
                .get()
                .uri("/patients/{id}", id)
                .retrieve()
                .onStatus(
                        status -> status.value() == 404,
                        (request, response) -> {
                            throw new PatientNotFoundException(id);
                        }
                )
                .body(PatientDto.class);
    }

}
