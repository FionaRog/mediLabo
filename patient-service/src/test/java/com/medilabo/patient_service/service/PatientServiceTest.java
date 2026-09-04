package com.medilabo.patient_service.service;


import com.medilabo.patient_service.dto.PatientDto;
import com.medilabo.patient_service.exception.PatientNotFoundException;
import com.medilabo.patient_service.mapper.PatientMapper;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientMapper patientMapper;

    private IPatientService patientService;

    @BeforeEach
    public void setUp() {

        patientService = new PatientService(patientRepository, patientMapper);
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

        PatientDto patientDto = new PatientDto();
        patientDto.setId(1);
        patientDto.setFirstname("John");
        patientDto.setLastname("Doe");
        patientDto.setDateOfBirth(LocalDate.of(1993, 1, 1));
        patientDto.setGender("F");

        when(patientRepository.findById(1)).thenReturn(Optional.of(patient));

        when(patientMapper.toDto(patient)).thenReturn(patientDto);

        PatientDto result = patientService.findPatientById(1);

        assertEquals(1,result.getId());
        assertEquals("John",result.getFirstname());

        verify(patientRepository).findById(1);
        verify(patientMapper).toDto(patient);
    }

    @Test
    @DisplayName("Should return exception when patient is not found")
    public void findPatientByIdNotFoundTest() {

        when(patientRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PatientNotFoundException.class , () -> patientService.findPatientById(1));

        verify(patientRepository).findById(1);
        verifyNoInteractions(patientMapper);
    }

    @Test
    @DisplayName("Should create a new Patient")
    public void createPatientTest() {
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstname("John");
        patientDto.setLastname("Doe");
        patientDto.setDateOfBirth(LocalDate.of(1993, 1, 1));
        patientDto.setGender("F");

        Patient patient = new Patient();
        patient.setFirstname("John");
        patient.setLastname("Doe");
        patient.setDateOfBirth(LocalDate.of(1993,01,01));
        patient.setGender(Gender.F);

        Patient savedPatient = new Patient();
        savedPatient.setId(1);
        savedPatient.setFirstname("John");
        savedPatient.setLastname("Doe");
        savedPatient.setDateOfBirth(LocalDate.of(1993, 1, 1));
        savedPatient.setGender(Gender.F);

        PatientDto savedPatientDto = new PatientDto();
        savedPatientDto.setId(1);
        savedPatientDto.setFirstname("John");
        savedPatientDto.setLastname("Doe");
        savedPatientDto.setDateOfBirth(LocalDate.of(1993, 1, 1));
        savedPatientDto.setGender("F");

        when(patientMapper.toEntity(patientDto)).thenReturn(patient);

        when(patientRepository.save(patient)).thenReturn(savedPatient);

        when(patientMapper.toDto(savedPatient)).thenReturn(savedPatientDto);

        PatientDto result = patientService.addNewPatient(patientDto);

        assertEquals(1, result.getId());
        assertEquals("John", result.getFirstname());

        verify(patientMapper).toEntity(patientDto);
        verify(patientRepository).save(patient);
        verify(patientMapper).toDto(savedPatient);
    }

    @Test
    @DisplayName("Should update a new Patient")
    public void updatePatientTest() {
        Patient previousPatient = new Patient();
        previousPatient.setId(1);
        previousPatient.setFirstname("John");
        previousPatient.setLastname("Doe");
        previousPatient.setDateOfBirth(LocalDate.of(1993,01,01));
        previousPatient.setGender(Gender.F);
        previousPatient.setAddress("Old address");

        PatientDto updatedPatientDto = new PatientDto();
        updatedPatientDto.setFirstname("John");
        updatedPatientDto.setLastname("Doe");
        updatedPatientDto.setDateOfBirth(LocalDate.of(1993, 1, 1));
        updatedPatientDto.setGender("F");
        updatedPatientDto.setAddress("New address");

        PatientDto resultDto = new PatientDto();
        resultDto.setId(1);
        resultDto.setFirstname("John");
        resultDto.setLastname("Doe");
        resultDto.setDateOfBirth(LocalDate.of(1993, 1, 1));
        resultDto.setGender("F");
        resultDto.setAddress("New address");

        when(patientRepository.findById(1)).thenReturn(Optional.of(previousPatient));

        when(patientRepository.save(previousPatient)).thenReturn(previousPatient);

        when(patientMapper.toDto(previousPatient)).thenReturn(resultDto);

        PatientDto result = patientService.updatePatient(1,updatedPatientDto);

        assertEquals("New address",result.getAddress());

        verify(patientRepository).findById(1);
        verify(patientRepository).save(previousPatient);
        verify(patientMapper).toDto(previousPatient);
    }

    @Test
    @DisplayName("Should throw exception when updating a patient not found")
    void updatePatientNotFoundTest() {

        PatientDto updatedPatientDto = new PatientDto();

        when(patientRepository.findById(2))
                .thenReturn(Optional.empty());


        assertThrows(PatientNotFoundException.class, () -> patientService.updatePatient(2, updatedPatientDto));

        verify(patientRepository).findById(2);
        verify(patientRepository, never()).save(any(Patient.class));
        verifyNoInteractions(patientMapper);
    }

    @Test
    @DisplayName("Should return all patients")
    public void findAllPatientsTest() {
        Patient patient = new Patient();
        patient.setId(1);
        patient.setFirstname("John");

        PatientDto patientDto = new PatientDto();
        patientDto.setId(1);
        patientDto.setFirstname("John");

        when(patientRepository.findAll())
                .thenReturn(List.of(patient));

        when(patientMapper.toDto(patient))
                .thenReturn(patientDto);

        List<PatientDto> result =
                patientService.findAllPatients();

        assertEquals(1, result.size());
        assertEquals("John", result.getFirst().getFirstname());

        verify(patientRepository).findAll();
        verify(patientMapper).toDto(patient);
    }

}
