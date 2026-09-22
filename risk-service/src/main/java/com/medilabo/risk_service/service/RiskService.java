package com.medilabo.risk_service.service;

import com.medilabo.risk_service.client.NoteClient;
import com.medilabo.risk_service.client.PatientClient;
import com.medilabo.risk_service.dto.NoteDto;
import com.medilabo.risk_service.dto.PatientDto;
import com.medilabo.risk_service.model.RiskLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Service responsible for assessing a patient's diabetes risk level.
 * <p>
 * The assessment is based on the patient's age and gender, as well as
 * predefined trigger terms found in the patient's medical notes.
 * </p>
 */
@Slf4j
@Service
public class RiskService implements IRiskService {

    private static final List<String> TRIGGERS = List.of(
            "hémoglobine a1c",
            "microalbumine",
            "taille",
            "poids",
            "anormal",
            "cholestérol",
            "vertiges",
            "rechute",
            "réaction",
            "anticorps"
    );

    private final PatientClient patientClient;
    private final NoteClient noteClient;

    /**
     * Creates a risk service using the clients required to retrieve
     * patient information and medical notes.
     *
     * @param patientClient client used to retrieve patient information
     * @param noteClient client used to retrieve patient medical notes
     */
    public RiskService(PatientClient patientClient, NoteClient noteClient) {
        this.patientClient = patientClient;
        this.noteClient = noteClient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RiskLevel assessRisk(Integer patientId) {

        log.debug("Assessing diabetes risk for patient id {}", patientId);

        PatientDto patient = patientClient.getPatientById(patientId);
        List<NoteDto> notes = noteClient.getNotesByPatientId(patientId);

        int age = calculateAge(patient.getDateOfBirth());
        int triggerCount = countTriggers(notes);

        RiskLevel riskLevel =
                determineRiskLevel(age, patient.getGender(), triggerCount);

        log.info(
                "Diabetes risk assessed for patient id {}: {}",
                patientId,
                riskLevel
        );

        return riskLevel;    }

    private int countTriggers(List<NoteDto> notes) {
        Set<String> foundTriggers = new HashSet<>();

        for (NoteDto note : notes) {
            String noteText = note.getNote().toLowerCase(Locale.ROOT);

            for(String trigger : TRIGGERS) {
                if(noteText.contains(trigger)) {
                    foundTriggers.add(trigger);
                }
            }

            if(noteText.contains("fumeur") || noteText.contains("fumeuse") || noteText.contains("fumer")) {
                foundTriggers.add("fumeur");
            }
        }

        return foundTriggers.size();
    }


    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private RiskLevel determineRiskLevel(int age, String gender, int triggerCount) {
        if(age >= 30) {
            if(triggerCount >= 8) {
                return RiskLevel.EARLY_ONSET;
            }
            if(triggerCount >= 6) {
                return RiskLevel.IN_DANGER;
            }
            if(triggerCount >= 2) {
                return RiskLevel.BORDERLINE;
            }

            return RiskLevel.NONE;
        }

        if(gender.equals("M")) {
            if(triggerCount >= 5) {
                return RiskLevel.EARLY_ONSET;
            }
            if(triggerCount >= 3) {
                return RiskLevel.IN_DANGER;
            }

            return RiskLevel.NONE;
        }

        else {
            if (triggerCount >= 7) {
                return RiskLevel.EARLY_ONSET;
            }
            if (triggerCount >= 4) {
                return RiskLevel.IN_DANGER;
            }

            return RiskLevel.NONE;
        }
    }
}
