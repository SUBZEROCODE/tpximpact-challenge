package com.subzero.tpximpact_challenge.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.models.AliasWithUrlMapping;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/v1/url-shortener")
public class UrlShortenController {

    @PostMapping(value = "/shorten", consumes = "application/json")
    public ResponseEntity<String> shortenUrlRequest(@RequestBody String jsonBody) {
        JSONObject bodyAsJsonObject = new JSONObject(jsonBody);

        // String fullUrlProvided = bodyAsJsonObject.getString("fullUrl");
        Optional<String> customAliasOptional = Optional.ofNullable(bodyAsJsonObject.getString("customAlias"));

        if(customAliasOptional.isPresent()){
        }

        // try {
        //     // if(customAliasOptional.isPresent()){
        //         // Need to check if that alias has been used before, if it has then return 400.
        //         //return new ResponseEntity<String>("Invalid input or alias already taken", HttpStatus.BAD_REQUEST);

        //     // If not used, then return the url and save the new alias to the repo
            
        //     // Always need to check if the url has been saved before, if it hasn't then need to map the long url within datastore.

        //     // Application logic to shorten the URL will go here.
        //     // Need to also add repo method logic next
        // } catch (MalformedURLException e) {
        //     return new ResponseEntity<String>("Invalid input or alias already taken", HttpStatus.BAD_REQUEST);
        // }

        // Only if successfull and not taken : `return new ResponseEntity<String>("URL successfully shortened", HttpStatus.CREATED)`;
        return new ResponseEntity<String>("Invalid input or alias already taken", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/urls")
    public ResponseEntity<AliasWithUrlMapping[]> listAllShortenedUrls() {
        // TODO: Implement repository logic to return all shortened urls with repo.findAll().
        return new ResponseEntity<AliasWithUrlMapping[]>(new AliasWithUrlMapping[]{},HttpStatus.OK);
    }
 
}
