package com.subzero.tpximpact_challenge.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.service.AliasUrlMappingService;
import com.subzero.tpximpact_challenge.util.MockAliasUrlMappingBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(value = AliasMappingController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class AliasMappingControllerTest {

    @MockitoBean
    private AliasUrlMappingService aliasUrlMappingService;

    @Autowired
    private MockMvc mockMvc;

    private final String baseUrl = "/api/v1/url-shortener/"; // replace with actual path
    
    @Test
    void getUrlRedirectForAGivenAliasWithAMatchedAliasShouldReturnStatusFound() throws Exception {
        String testAlias = "some-test-alias";
         mockMvc.perform(get(String.format(baseUrl +"/%s", testAlias)))
                .andExpect(status().isFound())
                .andExpect(content().string("Redirect to the original URL"));
    }

    @Test
    void getUrlRedirectForAGivenAliasGivenNoMatchedAliasShouldReturnStatusNotFound() throws Exception {
        mockMvc.perform(get(String.format(baseUrl +"/%s", "another-alias")))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Alias not found"));
    }

    @Test
    void deleteShortenedUrlMatchingAliasShouldReturnNotFoundWhenAliasDoesNotExist() throws Exception {
        String alias = "";

        when(aliasUrlMappingService.findAliasBasedOnParameterAliasPassedIn(alias))
            .thenReturn(Optional.empty());

        mockMvc.perform(delete(String.format(baseUrl +"/%s", "non-existant-alias")))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Alias not found"));
    }


    @Test
    void deleteShortenedUrlMatchingAlias() throws Exception {
        String testAlias = "my-custom-alias";

        AliasWithUrlMapping mockMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();

        when(aliasUrlMappingService.findAliasBasedOnParameterAliasPassedIn(testAlias))
            .thenReturn(Optional.of(mockMapping));

        mockMvc.perform(delete(String.format("/api/v1/url-shortener/%s", testAlias)))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Successfully deleted"));
    }
}
