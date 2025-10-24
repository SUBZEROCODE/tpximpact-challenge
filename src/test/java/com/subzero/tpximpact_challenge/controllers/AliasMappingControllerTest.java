package com.subzero.tpximpact_challenge.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(value = AliasMappingController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class AliasMappingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // TODO: Tricky tests due to PathVariable in controller, maybe need to use MockHttpServletRequest directly and set the path instead. 
    
    @Test
    void getUrlRedirectForAGivenAliasWithAMatchedAliasShouldReturnStatusFound() throws Exception {
        String testAlias = "some-test-alias";
         mockMvc.perform(get(String.format("/api/v1/url-shortener/%s", testAlias)))
                .andExpect(status().isFound())
                .andExpect(content().string("Redirect to the original URL"));
    }

    @Test
    void getUrlRedirectForAGivenAliasGivenNoMatchedAliasShouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(get(String.format("/api/v1/url-shortener/%s", "another-alias")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Alias not found"));
    }

    @Test
    void deleteShortenedUrlMatchingAlias() throws Exception {
        //TODO: Currently only returning alias not found as need repo logic to be implemented to delete an entry.
        mockMvc.perform(delete(String.format("/api/v1/url-shortener/%s", "another-alias")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Alias not found"));
    }
}
