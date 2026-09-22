package com.medilabo.risk_service.service;

import com.medilabo.risk_service.model.RiskLevel;

/**
 * Defines the operations used to assess the diabetes risk level of a patient.
 */
public interface IRiskService {

    /**
     * Assesses the diabetes risk level of the specified patient based on
     * patient information and medical notes.
     *
     * @param patientId the identifier of the patient to assess
     * @return the calculated diabetes risk level
     */
    RiskLevel assessRisk(Integer patientId);
}
