package com.medilabo.patient_service.controller;

import com.medilabo.patient_service.dto.PatientDto;
import com.medilabo.patient_service.model.Patient;
import com.medilabo.patient_service.service.IPatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for patient management.
 * Provides endpoints to retrieve, create and update patients.
 */
@RestController
@RequestMapping("/patients")
public class PatientController {

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * Retrieves all patients.
     *
     * @return a response containing the list of all patients
     */
    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients(){
        return ResponseEntity.ok(patientService.findAllPatients());
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id the ID of the patient to retrieve
     * @return a response containing the requested patient
     */
    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Integer id){
       PatientDto patientDto = patientService.findPatientById(id);

       return ResponseEntity.ok(patientDto);
    }

    /**
     * Creates a new patient.
     *
     * @param patientDto the patient information to create
     * @return a response containing the created patient with HTTP status 201
     */
    @PostMapping
    public ResponseEntity<PatientDto> createPatient(@Valid @RequestBody PatientDto patientDto){
        PatientDto newPatient = patientService.addNewPatient(patientDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newPatient);
    }

    /**
     * Updates an existing patient.
     *
     * @param id the ID of the patient to update
     * @param patientDto the new patient information
     * @return a response containing the updated patient
     */
    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable Integer id, @Valid @RequestBody PatientDto patientDto){
        PatientDto updatedPatient = patientService.updatePatient(id, patientDto);

        return ResponseEntity.ok(updatedPatient);
    }
}
