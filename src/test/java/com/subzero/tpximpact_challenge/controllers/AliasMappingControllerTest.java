package com.subzero.tpximpact_challenge.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(AliasMappingController.class)
public class AliasMappingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // TODO: Tricky tests due to PathVariable in controller, maybe need to use MockHttpServletRequest directly and set the path instead. 
    
    // @Test
    // void checkAliasForUrlRedirectGivenTestAliasShouldReturnStatusFound() throws Exception {
    //     String testAlias = "some-test-alias";
    //     mockMvc.perform(get("/api/url-shortener/v1?alias=" + testAlias))
    //             .andExpect(status().isFound())
    //             .andExpect(content().string("Redirect to the original URL"));
    // }

    // @Test
    // void checkAliasForUrlRedirectGivenNoAliasShouldReturnStatusNotFound() throws Exception {
    //     mockMvc.perform(get("/api/url-shortener/v1/alias=another-alias"))
    //             .andExpect(status().isNotFound())
    //             .andExpect(content().string("Alias not found"));
    // }
}
