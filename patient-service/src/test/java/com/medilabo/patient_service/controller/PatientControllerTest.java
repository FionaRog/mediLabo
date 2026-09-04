package com.medilabo.patient_service.controller;

import com.medilabo.patient_service.dto.PatientDto;
import com.medilabo.patient_service.exception.PatientNotFoundException;
import com.medilabo.patient_service.service.IPatientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IPatientService patientService;

    @Test
    @DisplayName("Should display Patient when ID is given")
    void displayPatientById() throws Exception {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(1);
        patientDto.setFirstname("John");
        patientDto.setLastname("Doe");
        patientDto.setAddress("test address");
        patientDto.setGender("F");

        when(patientService.findPatientById(1)).thenReturn(patientDto);

        mockMvc.perform(get("/patients/{id}",1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"));

        verify(patientService).findPatientById(1);
    }

    @Test
    @DisplayName("Should return 404 when patient is not found")
    void displayPatientNotFound() throws Exception {

        when(patientService.findPatientById(2))
                .thenThrow(new PatientNotFoundException(2));

        mockMvc.perform(get("/patients/{id}",2))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Patient not found with id 2"));

        verify(patientService).findPatientById(2);
    }

    @Test
    @DisplayName("Should return Patient when Patient is created")
    void displayAddedPatient() throws Exception {
        PatientDto patientDto = new PatientDto();
        patientDto.setFirstname("John");
        patientDto.setLastname("Doe");
        patientDto.setDateOfBirth(LocalDate.of(1999,01,01));
        patientDto.setGender("M");

        when(patientService.addNewPatient(any(PatientDto.class))).thenReturn(patientDto);

        mockMvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"));


        verify(patientService).addNewPatient(argThat(p ->
                        p.getFirstname().equals("John")
                        && p.getLastname().equals("Doe")));
    }

    @Test
    @DisplayName("Should return Patient when Patient is updated")
    void displayUpdatedPatient() throws Exception {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(1);
        patientDto.setFirstname("John");
        patientDto.setLastname("Doe");
        patientDto.setDateOfBirth(LocalDate.of(1999,01,01));
        patientDto.setGender("M");

        when(patientService.updatePatient(eq(1), any(PatientDto.class))).thenReturn(patientDto);

        mockMvc.perform(put("/patients/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patientDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"));


        verify(patientService).updatePatient(eq(1), argThat(p ->
                p.getFirstname().equals("John")
                && p.getLastname().equals("Doe")
                && p.getDateOfBirth().equals(LocalDate.of(1999,01,01))
                && p.getGender().equals("M")));
    }
}
