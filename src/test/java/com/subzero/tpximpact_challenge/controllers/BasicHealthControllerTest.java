package com.subzero.tpximpact_challenge.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.security.autoconfigure.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.subzero.tpximpact_challenge.util.UpHealthConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(classes = {BasicHealthController.class, UpHealthConfig.class})
@ImportAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc
public class BasicHealthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthCheckGetShouldReturnStatusOKAndStringReadyToServeAPIBasedOnActuatorUP() throws Exception {
        mockMvc.perform(get("/api/v1/url-shortener/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Java Spring is ready to serve the API"));
    }

    // This mocked variant of the test is not possible currently due to 4.0.0-M1 milestone release, need to wait for full release.
    // @Test
    // void healthCheckGetShouldReturnStatusCodeWhenActuatorIsNotUP() throws Exception {
    //     HealthDescriptor mockHealthDescriptor = mock(HealthDescriptor.class);

    //     when(mockHealthDescriptor.getStatus()).thenReturn(Status.DOWN);
    //     when(healthEndpoint.health()).thenReturn(mockHealthDescriptor);

    //     mockMvc.perform(get("/api/v1/url-shortener/health"))
    //             .andExpect(status().isOk())
    //             .andExpect(content().string("Api has not yet finally initialised"));
    // }


}
