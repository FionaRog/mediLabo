package com.medilabo.risk_service.controller;

import com.medilabo.risk_service.model.RiskLevel;
import com.medilabo.risk_service.service.IRiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller exposing endpoints for diabetes risk assessment.
 */
@Slf4j
@RestController
@RequestMapping("/risk")
public class RiskController {

    private final IRiskService riskService;

    /**
     * Creates a controller using the service responsible for diabetes
     * risk assessment.
     *
     * @param riskService service used to assess patient risk
     */
    public RiskController(IRiskService riskService) {
        this.riskService = riskService;
    }

    /**
     * Retrieves the diabetes risk level for the specified patient.
     *
     * @param patientId the identifier of the patient to assess
     * @return a response containing the calculated risk level
     */
    @GetMapping("/{patientId}")
    public ResponseEntity<RiskLevel> getRisk(@PathVariable Integer patientId) {

        log.debug("Received diabetes risk assessment request for patient id {}", patientId);

        RiskLevel riskLevel = riskService.assessRisk(patientId);

        return ResponseEntity.ok(riskLevel);
    }

}
