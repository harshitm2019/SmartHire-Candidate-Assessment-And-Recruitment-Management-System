package com.apigateway.dto;

import java.time.Instant;

public record FallbackResponse(Instant timestamp, int status,String message) {



}
