package com.subzero.tpximpact_challenge.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(value = UrlShortenController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class UrlShortenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shortenUrlRequestPostEndpointShouldReturnBadRequestGivenNoImplementationCurrently() throws Exception {
        mockMvc.perform(post("/api/v1/url-shortener/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{customAlias: \"my-alias\", fullUrl: \"http://example.com/some/long/url\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input or alias already taken"));
    }

    @Test
    void listAllShortenedUrlsGetEndpointShouldReturnEmptyArray() throws Exception {
        mockMvc.perform(get("/api/v1/url-shortener/urls")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(content().string("[]"));
    }
}
