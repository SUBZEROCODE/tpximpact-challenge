package com.subzero.tpximpact_challenge.controllers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.security.autoconfigure.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.models.UrlShortenRequestDTO;
import com.subzero.tpximpact_challenge.service.AliasUrlMappingService;
import com.subzero.tpximpact_challenge.util.MockAliasUrlMappingBuilder;

@WebMvcTest(value = UrlShortenController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class UrlShortenControllerTest {

    @MockitoBean
    private AliasUrlMappingService aliasUrlMappingService;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shortenUrlRequestPostEndpointShouldReturn200AndSuccessWhenObjectCanBeSavedSuccessfully() throws Exception {
        AliasWithUrlMapping testAliasWithUrlMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();

        UrlShortenRequestDTO testRequestDTO = new UrlShortenRequestDTO();
        testRequestDTO.setFullUrl(testAliasWithUrlMapping.getFullUrl());
        testRequestDTO.setCustomAlias(testAliasWithUrlMapping.getAlias());

        ObjectMapper objectMapper = new ObjectMapper();

        // This mocks that the alias has not been saved before
        Mockito.when(aliasUrlMappingService.findAliasBasedOnParameterAliasPassedIn(testAliasWithUrlMapping.getAlias()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/url-shortener/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("URL successfully shortened"));
    }

    @Test
    public void shortenUrlRequestPostEndpointShouldReturnBadRequestWhenInvalidDataIsSentToDTO() throws Exception {
        AliasWithUrlMapping testAliasWithUrlMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();

        JSONObject invalidJSONTest = new JSONObject();
        invalidJSONTest.put("fullUl", testAliasWithUrlMapping.getFullUrl());

        // Mocked so does not need to return anything.
        Mockito.when(aliasUrlMappingService.findAliasBasedOnParameterAliasPassedIn(testAliasWithUrlMapping.getAlias()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/url-shortener/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJSONTest.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                    result.getResolvedException() instanceof MethodArgumentNotValidException
                ));
    }

    @Test
    public void shortenUrlRequestPostEndpointShouldReturnBadRequestIfAliasAlreadyExists() throws Exception {
        AliasWithUrlMapping testAliasWithUrlMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();

        UrlShortenRequestDTO testRequestDTO = new UrlShortenRequestDTO();
        testRequestDTO.setFullUrl(testAliasWithUrlMapping.getFullUrl());
        testRequestDTO.setCustomAlias(testAliasWithUrlMapping.getAlias());

        ObjectMapper objectMapper = new ObjectMapper();

        // Mocked so does not need to return anything.
        Mockito.when(aliasUrlMappingService.findAliasBasedOnParameterAliasPassedIn(testAliasWithUrlMapping.getAlias()))
                .thenReturn(Optional.of(testAliasWithUrlMapping));

        mockMvc.perform(post("/api/v1/url-shortener/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input or alias already taken"));
    }


     @Test
    public void shortenUrlRequestPostEndpointShouldReturnSuccessWhenGeneratedAliasSavedSuccessfully() throws Exception {
        AliasWithUrlMapping expectedAliasWithUrlMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();

        UrlShortenRequestDTO testRequestDTO = new UrlShortenRequestDTO();
        testRequestDTO.setFullUrl(expectedAliasWithUrlMapping.getFullUrl());

        ObjectMapper objectMapper = new ObjectMapper();

        // Adapt expectedUrlMapping to include the "generated alias" 
        // This is "mini-" the number of items in AliasUrlMappingRepository + 1
        expectedAliasWithUrlMapping.setShortUrl("http://localhost:8080/mini-1");
        expectedAliasWithUrlMapping.setAlias("mini-1");

        mockMvc.perform(post("/api/v1/url-shortener/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().string("URL successfully shortened"));

        verify(aliasUrlMappingService).saveAliasWithUrlMappingToRepo(expectedAliasWithUrlMapping);
    }

    @Test
    public void shortenUrlRequestPostEndpointShouldReturnBadRequestWhenMalformedJsonIsSentToDTO() throws Exception {
        AliasWithUrlMapping testAliasWithUrlMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();

        String malformedJson = "{ \"fullUrl\": \"http://example.com\", \"customAlias\": ";

        // Mocked so does not need to return anything.
        Mockito.when(aliasUrlMappingService.findAliasBasedOnParameterAliasPassedIn(testAliasWithUrlMapping.getAlias()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/url-shortener/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(malformedJson))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                    result.getResolvedException() instanceof HttpMessageNotReadableException
                ));
    }

    @Test
    public void shortenUrlRequestPostEndpointShouldReturnBadRequestIfExceptionFromService() throws Exception {
        AliasWithUrlMapping testAliasWithUrlMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();

        UrlShortenRequestDTO testRequestDTO = new UrlShortenRequestDTO();
        testRequestDTO.setFullUrl(testAliasWithUrlMapping.getFullUrl());
        testRequestDTO.setCustomAlias(testAliasWithUrlMapping.getAlias());

        ObjectMapper objectMapper = new ObjectMapper();

        Mockito.when(aliasUrlMappingService.findAliasBasedOnParameterAliasPassedIn(testAliasWithUrlMapping.getAlias()))
                .thenReturn(Optional.empty());

        Mockito.doThrow(new RuntimeException("DB error occurred"))
                .when(aliasUrlMappingService).saveAliasWithUrlMappingToRepo(any(AliasWithUrlMapping.class));

        mockMvc.perform(post("/api/v1/url-shortener/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRequestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input or alias already taken"));
    }

    @Test
    void listAllShortenedUrlsGetEndpointShouldReturnEmptyArrayIfNoRecordsFound() throws Exception {
        mockMvc.perform(get("/api/v1/url-shortener/urls")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void listAllShortenedUrlsGetEndpointShouldReturnArrayMatchingTheFindAllOnTheRepo() throws Exception {
        AliasWithUrlMapping mockMapping = MockAliasUrlMappingBuilder.getStubbedAliasWithUrlMappingForTesting();

        when(aliasUrlMappingService.findAllAliasWithUrlMappingRecordsInRepo()).thenReturn(List.of(mockMapping));

        String expectedResult = "[{\"alias\":\"my-custom-alias\",\"fullUrl\":\"https://example.com/very/long/url\",\"shortUrl\":\"http://localhost:8080/my-custom-alias\"}]";

        mockMvc.perform(get("/api/v1/url-shortener/urls")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }
}
