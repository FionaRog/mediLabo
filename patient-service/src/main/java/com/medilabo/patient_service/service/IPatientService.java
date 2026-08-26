package com.medilabo.patient_service.service;

import com.medilabo.patient_service.model.Patient;

public interface IPatientService {

    Patient addNewPatient(Patient patient);

    public Patient updatePatient(Integer id, Patient patient);

    public Patient findPatientById(Integer id);
}
