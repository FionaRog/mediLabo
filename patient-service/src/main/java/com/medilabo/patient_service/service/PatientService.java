package com.medilabo.patient_service.service;

import com.medilabo.patient_service.exception.PatientNotFoundException;
import com.medilabo.patient_service.model.Patient;
import com.medilabo.patient_service.repository.PatientRepository;
import org.springframework.stereotype.Service;



@Service
public class PatientService implements IPatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient findPatientById(Integer id) {
        return patientRepository.findById(id).
                orElseThrow(() -> new PatientNotFoundException(id));
    }

    public Patient addNewPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Patient updatePatient(Integer id, Patient patient) {
        Patient updatedPatient = findPatientById(id);

        updatedPatient.setFirstname(patient.getFirstname());
        updatedPatient.setLastname(patient.getLastname());
        updatedPatient.setDateOfBirth(patient.getDateOfBirth());
        updatedPatient.setGender(patient.getGender());
        updatedPatient.setAddress(patient.getAddress());
        updatedPatient.setTelephone(patient.getTelephone());

        return patientRepository.save(updatedPatient);
    }


}
