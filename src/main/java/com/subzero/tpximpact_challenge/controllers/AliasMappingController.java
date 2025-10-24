package com.subzero.tpximpact_challenge.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.service.AliasUrlMappingService;

@RestController
@RequestMapping("/api/v1/url-shortener/{alias}")
public class AliasMappingController {

    @Autowired
    private AliasUrlMappingService aliasUrlMappingService;

    @GetMapping("")
    public ResponseEntity<String> getUrlRedirectForAGivenAlias(@PathVariable String alias) {
        // @PathVariable has a default of true for required, so no need to explicitly set it.
        
        // TODO: Temporary hardcoded "alias" for testing purpose along with println.
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

    @DeleteMapping("")
    public ResponseEntity<String> deleteShortenedUrlMatchingAlias(@PathVariable String alias) {
        Optional<AliasWithUrlMapping> aliasUrlMappingToDelete = aliasUrlMappingService.findAliasBasedOnParameterAliasPassedIn(alias);

        if(!aliasUrlMappingToDelete.isPresent()){
            return new ResponseEntity<String>("Alias not found", HttpStatus.NOT_FOUND);
        }

        // This then means we can deal with the fact we definitely have the required object in the logic.
        try {
            aliasUrlMappingService.deleteByAliasFoundInRepo(aliasUrlMappingToDelete.get());
        
        } catch (Exception exception) {
            // We want to throw as this means that an exception occured and the state of the DB may not be as we intend.
            throw exception;
        }
        // Only return success if its actually been deleted without exceptions which could occur.
        return new ResponseEntity<String>("Successfully deleted", HttpStatus.NO_CONTENT);
    }
}
