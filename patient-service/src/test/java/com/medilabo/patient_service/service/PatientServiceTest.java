package com.medilabo.patient_service.service;


import com.medilabo.patient_service.exception.PatientNotFoundException;
import com.medilabo.patient_service.model.Patient;
import com.medilabo.patient_service.model.enums.Gender;
import com.medilabo.patient_service.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    private IPatientService patientService;

    @BeforeEach
    public void setUp() {
        patientService = new PatientService(patientRepository);
    }

    @Test
    @DisplayName("Should return patient by ID")
    public void findPatientByIdTest() {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstname("John");
        patient.setLastname("Doe");
        patient.setDateOfBirth(LocalDate.of(1993,01,01));
        patient.setGender(Gender.F);

        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

        Patient result = patientService.findPatientById(1);

        assertEquals(1,result.getId());
        assertEquals("John",result.getFirstname());
        verify(patientRepository).findById(1);
    }

    @Test
    @DisplayName("Should return exception when patient is not found")
    public void findPatientByIdNotFoundTest() {

        when(patientRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class , () -> patientService.findPatientById(1));

        verify(patientRepository).findById(1);
    }

    @Test
    @DisplayName("Should create a new Patient")
    public void createPatientTest() {
        Patient patient = new Patient();
        patient.setFirstname("John");
        patient.setLastname("Doe");
        patient.setDateOfBirth(LocalDate.of(1993,01,01));
        patient.setGender(Gender.F);

        when(patientRepository.save(patient)).thenReturn(patient);

        Patient result = patientService.addNewPatient(patient);

        assertSame(patient,result);

        verify(patientRepository).save(patient);
    }

    @Test
    @DisplayName("Should update a new Patient")
    public void updatePatientTest() {
        Patient previousPatient = new Patient();
        previousPatient.setFirstname("John");
        previousPatient.setLastname("Doe");
        previousPatient.setDateOfBirth(LocalDate.of(1993,01,01));
        previousPatient.setGender(Gender.F);
        previousPatient.setAddress("Old address");

        when(patientRepository.findById(1)).thenReturn(Optional.of(previousPatient));

        Patient updatedPatient = new Patient();
        updatedPatient.setFirstname("John");
        updatedPatient.setLastname("Doe");
        updatedPatient.setDateOfBirth(LocalDate.of(1993,01,01));
        updatedPatient.setGender(Gender.F);
        updatedPatient.setAddress("New address");

        when(patientRepository.save(previousPatient)).thenReturn(previousPatient);

        Patient result = patientService.updatePatient(1,updatedPatient);

        assertEquals("New address",result.getAddress());

        verify(patientRepository).findById(1);
        verify(patientRepository).save(previousPatient);
    }

    @Test
    @DisplayName("Should throw exception when updating a patient not found")
    void updatePatientNotFoundTest() {

        Patient updatedPatient = new Patient();

        when(patientRepository.findById(2))
                .thenReturn(Optional.empty());


        assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(2, updatedPatient));

        verify(patientRepository).findById(2);
        verify(patientRepository, never()).save(any(Patient.class));
    }

}
