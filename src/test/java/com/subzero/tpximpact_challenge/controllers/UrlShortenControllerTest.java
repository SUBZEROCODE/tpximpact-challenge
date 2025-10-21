package com.subzero.tpximpact_challenge.controllers;

@WebMvcTest(UrlShortenController.class)
public class UrlShortenControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shortenUrlRequestPostEndpointShouldReturnBadRequestGivenNoImplementationCurrently() throws Exception {
        mockMvc.perform(get("/api/url-shortener/v1/shorten"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid input or alias already taken"));
    }
}
