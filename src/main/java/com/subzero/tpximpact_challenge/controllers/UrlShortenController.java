package com.subzero.tpximpact_challenge.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.service.AliasUrlMappingService;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/url-shortener")
public class UrlShortenController {

    @Autowired
    private AliasUrlMappingService aliasUrlMappingService;

    @PostMapping(value = "/shorten", consumes = "application/json")
    public ResponseEntity<String> shortenUrlRequest(@RequestBody String jsonBody) {
        JSONObject bodyAsJsonObject = new JSONObject(jsonBody);

        String fullUrlProvided = bodyAsJsonObject.getString("fullUrl");
        Optional<String> customAliasOptional = Optional.ofNullable(bodyAsJsonObject.getString("customAlias"));

        if(!customAliasOptional.isPresent()){
            return new ResponseEntity<String>("Invalid input or alias already taken", HttpStatus.BAD_REQUEST);
        }
        
        String customAliasProvided = customAliasOptional.get();

        // Check if alias has been saved before
        Optional<AliasWithUrlMapping> aliasWithUrlMappingStoredInDb = aliasUrlMappingService.findAliasBasedOnParameterAliasPassedIn(customAliasProvided);

        if(aliasWithUrlMappingStoredInDb.isPresent()){
            return new ResponseEntity<String>("Invalid input or alias already taken", HttpStatus.BAD_REQUEST);
        }

        try {
            AliasWithUrlMapping aliasToSave = new AliasWithUrlMapping(customAliasProvided, fullUrlProvided);
            aliasUrlMappingService.saveAliasWithUrlMappingToRepo(aliasToSave);
        } catch (Exception e){
                return new ResponseEntity<String>("Invalid input or alias already taken", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("URL successfully shortened", HttpStatus.CREATED);
    }

    @GetMapping("/urls")
    public ResponseEntity<AliasWithUrlMapping[]> listAllShortenedUrls() {
        AliasWithUrlMapping[] aliasWithUrlMappingsArray = aliasUrlMappingService.findAllAliasWithUrlMappingRecordsInRepo().toArray(AliasWithUrlMapping[]::new);
        return new ResponseEntity<AliasWithUrlMapping[]>(aliasWithUrlMappingsArray, HttpStatus.OK);
    }
 
}