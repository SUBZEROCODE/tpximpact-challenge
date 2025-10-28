package com.subzero.tpximpact_challenge.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subzero.tpximpact_challenge.models.AliasWithUrlMapping;
import com.subzero.tpximpact_challenge.models.UrlShortenRequestDTO;
import com.subzero.tpximpact_challenge.service.AliasUrlMappingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/url-shortener")
public class UrlShortenController {

    @Autowired
    private AliasUrlMappingService aliasUrlMappingService;

    @PostMapping(value = "/shorten", consumes = "application/json")
    public ResponseEntity<String> shortenUrlRequest(@Valid @RequestBody UrlShortenRequestDTO urlShortenRequestDTO) {
        Optional<String> customAliasOptional;
        String fullUrlProvided;
        boolean aliasGenerated = false;

        // JSON serialisation is always something which can go wrong
        // Or the required parameters are not provided "getString()" can also return JSONException.
        try{
            fullUrlProvided = urlShortenRequestDTO.getFullUrl();
            customAliasOptional = Optional.ofNullable(urlShortenRequestDTO.getCustomAlias());

            // If no customAlias provided we will generate one and save that back as an Optional for below logic.
            if(!customAliasOptional.isPresent()){
                // This means that the generated alias will always be the number of elements in DB, if 2 already - then it will be 3 (never colliding)
                int currentSizeOfDB = aliasUrlMappingService.findAllAliasWithUrlMappingRecordsInRepo().size() + 1;
                customAliasOptional = Optional.of("mini-" + String.valueOf(currentSizeOfDB));
                aliasGenerated = true;
            }

        } catch (HttpMessageNotReadableException e){
            return new ResponseEntity<>("Invalid input or alias already taken", HttpStatus.BAD_REQUEST);
        }
        
        String customAliasProvided = customAliasOptional.get();

        // Check if alias has been saved before
        Optional<AliasWithUrlMapping> aliasWithUrlMappingStoredInDb = aliasUrlMappingService.findAliasBasedOnParameterAliasPassedIn(customAliasProvided);

        if(aliasWithUrlMappingStoredInDb.isPresent()){
            // If generated and collides with a previously added alias
            if(aliasGenerated) {
                customAliasProvided = customAliasProvided + "ford";
            } else {
                return new ResponseEntity<String>("Invalid input or alias already taken", HttpStatus.BAD_REQUEST);
            }
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