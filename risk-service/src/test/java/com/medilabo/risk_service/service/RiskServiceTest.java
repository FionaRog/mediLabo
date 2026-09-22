package com.medilabo.risk_service.service;

import com.medilabo.risk_service.client.NoteClient;
import com.medilabo.risk_service.client.PatientClient;
import com.medilabo.risk_service.dto.NoteDto;
import com.medilabo.risk_service.dto.PatientDto;
import com.medilabo.risk_service.model.RiskLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RiskServiceTest {

    @Mock
    private PatientClient patientClient;

    @Mock
    private NoteClient noteClient;

    @InjectMocks
    private RiskService riskService;

    private PatientDto createPatient(int age, String gender) {
        PatientDto patient = new PatientDto();
        patient.setId(1);
        patient.setDateOfBirth(LocalDate.now().minusYears(age));
        patient.setGender(gender);

        return patient;
    }

    private NoteDto createNote(String text) {
        NoteDto note = new NoteDto();
        note.setNote(text);

        return note;
    }

    @Test
    @DisplayName("Should return NONE if 30 or older and 1 trigger")
    void shouldReturnNoneWhenPatientIsOver30WithOneTrigger() {
        PatientDto patient = createPatient(40, "M");
        List<NoteDto> notes = List.of(
                createNote("poids"),
                createNote("poids")
        );

        when(patientClient.getPatientById(1)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(1)).thenReturn(notes);

        RiskLevel result = riskService.assessRisk(1);

        assertEquals(RiskLevel.NONE, result);
    }

    @Test
    @DisplayName("Should return BORDERLINE when patient is 30 or older with 2 triggers")
    void shouldReturnBorderlineWhenPatientIs30OrOlderWithTwoTriggers() {

        PatientDto patient = createPatient(40, "F");
        List<NoteDto> notes = List.of(
                createNote("POIDS cholestérol")
        );

        when(patientClient.getPatientById(1)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(1)).thenReturn(notes);

        RiskLevel result = riskService.assessRisk(1);

        assertEquals(RiskLevel.BORDERLINE, result);
    }

    @Test
    @DisplayName("Should return IN_DANGER when patient is 30 or older with 6 triggers")
    void shouldReturnInDangerWhenPatientIs30OrOlderWithSixTriggers() {

        PatientDto patient = createPatient(40, "M");
        List<NoteDto> notes = List.of(
                createNote(
                        "poids cholestérol anormal taille vertiges rechute"
                )
        );

        when(patientClient.getPatientById(1)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(1)).thenReturn(notes);

        RiskLevel result = riskService.assessRisk(1);

        assertEquals(RiskLevel.IN_DANGER, result);
    }

    @Test
    @DisplayName("Should return EARLY_ONSET when patient is 30 or older with 8 triggers")
    void shouldReturnEarlyOnsetWhenPatientIs30OrOlderWithEightTriggers() {

        PatientDto patient = createPatient(40, "F");
        List<NoteDto> notes = List.of(
                createNote(
                        "poids cholestérol anormal taille vertiges rechute réaction anticorps"
                )
        );

        when(patientClient.getPatientById(1)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(1)).thenReturn(notes);

        RiskLevel result = riskService.assessRisk(1);

        assertEquals(RiskLevel.EARLY_ONSET, result);
    }

    @Test
    @DisplayName("Should return IN_DANGER when male under 30 has 3 triggers")
    void shouldReturnInDangerWhenMaleUnder30HasThreeTriggers() {

        PatientDto patient = createPatient(25, "M");
        List<NoteDto> notes = List.of(
                createNote("poids cholestérol anormal")
        );

        when(patientClient.getPatientById(1)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(1)).thenReturn(notes);

        RiskLevel result = riskService.assessRisk(1);

        assertEquals(RiskLevel.IN_DANGER, result);
    }

    @Test
    @DisplayName("Should return EARLY_ONSET when male under 30 has 5 triggers")
    void shouldReturnEarlyOnsetWhenMaleUnder30HasFiveTriggers() {

        PatientDto patient = createPatient(25, "M");
        List<NoteDto> notes = List.of(
                createNote("poids cholestérol anormal taille vertiges")
        );

        when(patientClient.getPatientById(1)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(1)).thenReturn(notes);

        RiskLevel result = riskService.assessRisk(1);

        assertEquals(RiskLevel.EARLY_ONSET, result);
    }

    @Test
    @DisplayName("Should return IN_DANGER when female under 30 has 4 triggers")
    void shouldReturnInDangerWhenFemaleUnder30HasFourTriggers() {

        PatientDto patient = createPatient(25, "F");
        List<NoteDto> notes = List.of(
                createNote("poids cholestérol anormal taille")
        );

        when(patientClient.getPatientById(1)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(1)).thenReturn(notes);

        RiskLevel result = riskService.assessRisk(1);

        assertEquals(RiskLevel.IN_DANGER, result);
    }

    @Test
    @DisplayName("Should return EARLY_ONSET when female under 30 has 7 triggers")
    void shouldReturnEarlyOnsetWhenFemaleUnder30HasSevenTriggers() {

        PatientDto patient = createPatient(25, "F");
        List<NoteDto> notes = List.of(
                createNote(
                        "poids cholestérol anormal taille vertiges rechute réaction"
                )
        );

        when(patientClient.getPatientById(1)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(1)).thenReturn(notes);

        RiskLevel result = riskService.assessRisk(1);

        assertEquals(RiskLevel.EARLY_ONSET, result);
    }

    @Test
    @DisplayName("Should count smoking variants as one trigger")
    void shouldCountSmokingVariantsAsOneTrigger() {

        PatientDto patient = createPatient(40, "M");

        List<NoteDto> notes = List.of(
                createNote("Patient fumeur"),
                createNote("Patiente fumeuse"),
                createNote("Conseil pour arrêter de fumer")
        );

        when(patientClient.getPatientById(1)).thenReturn(patient);
        when(noteClient.getNotesByPatientId(1)).thenReturn(notes);

        RiskLevel result = riskService.assessRisk(1);

        assertEquals(RiskLevel.NONE, result);
    }
}


