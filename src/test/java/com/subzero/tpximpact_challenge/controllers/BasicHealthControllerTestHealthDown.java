package com.subzero.tpximpact_challenge.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.security.autoconfigure.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;

import com.subzero.tpximpact_challenge.util.DownHealthConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = {BasicHealthController.class, DownHealthConfig.class})
@ImportAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class BasicHealthControllerTestHealthDown {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthCheckGetShouldReturnStatusCodeWhenActuatorIsNotUP() throws Exception {
        // Actuator has not been properly added to test so it returns 500.
        mockMvc.perform(get("/api/v1/url-shortener/health"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Health check failed"));
    }

}
