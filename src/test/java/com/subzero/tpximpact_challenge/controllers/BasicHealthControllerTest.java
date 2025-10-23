package com.subzero.tpximpact_challenge.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(value = BasicHealthController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class BasicHealthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthCheckGetShouldReturnStatusOKAndStringReadyToServeAPI() throws Exception {
        //TODO: Context needs fixing so that the path is correctly being inherited in tests.
        mockMvc.perform(get("/api/v1/url-shortener/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Java Spring is ready to serve the API"));
    }

}
