package com.subzero.tpximpact_challenge.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This class will initially implemented as a basic controller test, but aiming to be expanded to use actuator endpoints in the future.
@RestController
@RequestMapping("/api/v1/url-shortener")
public class BasicHealthController {

        @GetMapping("/health")
        public ResponseEntity<String> healthCheck() {
            return new ResponseEntity<String>("Java Spring is ready to serve the API", HttpStatus.OK);
        }
    
}
