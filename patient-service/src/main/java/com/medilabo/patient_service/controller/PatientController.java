package com.medilabo.patient_service.controller;

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
    public ResponseEntity<List<Patient>> getAllPatients(){
        return ResponseEntity.ok(patientService.findAllPatients());
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id the ID of the patient to retrieve
     * @return a response containing the requested patient
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Integer id){
       Patient patient = patientService.findPatientById(id);

       return ResponseEntity.ok(patient);
    }

    /**
     * Creates a new patient.
     *
     * @param patient the patient information to create
     * @return a response containing the created patient with HTTP status 201
     */
    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient){
        Patient newPatient = patientService.addNewPatient(patient);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newPatient);
    }

    /**
     * Updates an existing patient.
     *
     * @param id the ID of the patient to update
     * @param patient the new patient information
     * @return a response containing the updated patient
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Integer id, @Valid @RequestBody Patient patient){
        Patient updatedPatient = patientService.updatePatient(id, patient);

        return ResponseEntity.ok(updatedPatient);
    }
}
