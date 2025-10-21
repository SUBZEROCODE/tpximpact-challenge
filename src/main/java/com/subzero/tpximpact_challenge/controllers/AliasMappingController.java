package com.subzero.tpximpact_challenge.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url-shortener/v1/{alias}")
public class AliasMappingController {
    
    @GetMapping("")
    public ResponseEntity<String> checkAliasForUrlRedirect(@PathVariable String alias) {
        String myAlias = "some-test-alias";

        System.out.println("Alias received: " +  alias);

        // TODO: Will need to check the datastore for the alias and return the correct url redirect
        if(alias.equals(myAlias)){
            return new ResponseEntity<String>("Redirect to the original URL", HttpStatus.FOUND);
        } else {
            return new ResponseEntity<String>("Alias not found", HttpStatus.NOT_FOUND);
        }

        // if(request.getServletPath() is found in datastore) {
        //     return new ResponseEntity<String>("Redirecting to full URL", HttpStatus.FOUND);
        // }
    }
}
