package com.subzero.tpximpact_challenge.controllers;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This class will initially implemented as a basic controller test, but aiming to be expanded to use actuator endpoints in the future.
@RestController
@RequestMapping("/api/v1/url-shortener")
public class BasicHealthController {
    private final HealthEndpoint healthEndpoint;

    public BasicHealthController(HealthEndpoint healthEndpoint){
        this.healthEndpoint = healthEndpoint;

    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        try {
            String healthStatusCode = healthEndpoint.health().getStatus().getCode();

            if(healthStatusCode.equalsIgnoreCase("UP")){
                return new ResponseEntity<String>("Java Spring is ready to serve the API", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Api has not yet finally initialised", HttpStatus.valueOf(healthStatusCode));
            }
        } catch(Exception exception) {
            return new ResponseEntity<>("Health check failed", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    
}
