package com.medilabo.risk_service.controller;

import com.medilabo.risk_service.model.RiskLevel;
import com.medilabo.risk_service.service.IRiskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RiskController.class)
public class RiskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IRiskService riskService;

    @Test
    @DisplayName("Should return patient risk level")
    void getPatientRiskLevelTest() throws Exception {
        when(riskService.assessRisk(1)).thenReturn(RiskLevel.IN_DANGER);

        mockMvc.perform(get("/risk/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("\"IN_DANGER\""));
    }
}
