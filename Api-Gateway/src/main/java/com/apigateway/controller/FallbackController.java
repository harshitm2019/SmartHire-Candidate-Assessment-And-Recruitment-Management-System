package com.apigateway.controller;


import com.apigateway.constants.ApiConstants;
import com.apigateway.dto.FallbackResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping(ApiConstants.FALLBACK_BASE_API)
public class FallbackController {

    @GetMapping(ApiConstants.FALLBACK_AUTH_SERVICE)
    public ResponseEntity<FallbackResponse> authServiceFallback() {

        FallbackResponse response = new FallbackResponse(
                Instant.now(),
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                ApiConstants.AUTH_SERVICE_UNAVAILABLE
        );

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }


}
