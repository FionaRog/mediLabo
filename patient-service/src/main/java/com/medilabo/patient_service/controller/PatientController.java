package com.medilabo.patient_service.controller;

import com.medilabo.patient_service.model.Patient;
import com.medilabo.patient_service.service.IPatientService;
import com.medilabo.patient_service.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/patients")
public class PatientController {

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Integer id){
       Patient patient = patientService.findPatientById(id);

       return ResponseEntity.ok(patient);
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient){
        Patient newPatient = patientService.addNewPatient(patient);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newPatient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Integer id, @Valid @RequestBody Patient patient){
        Patient updatedPatient = patientService.updatePatient(id, patient);

        return ResponseEntity.ok(updatedPatient);
    }
}
