package com.subzero.tpximpact_challenge.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(UrlShortenController.class)
public class UrlShortenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void shortenUrlRequestPostEndpointShouldReturnBadRequestGivenNoImplementationCurrently() throws Exception {
        mockMvc.perform(post("/api/url-shortener/v1/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{customAlias: \"my-alias\", fullUrl: \"http://example.com/some/long/url\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input or alias already taken"));
    }
}
