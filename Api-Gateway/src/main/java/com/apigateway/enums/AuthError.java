package com.apigateway.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
public enum AuthError {

    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED,"Token expired"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED,"Invalid token"),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED,"Invalid signature"),
    INVALID_TOKEN_FORMAT(HttpStatus.BAD_REQUEST,"Invalid token format"),
    MISSING_HEADER(HttpStatus.BAD_REQUEST,"Missing header"),
    FORBIDDEN(HttpStatus.BAD_REQUEST,"Forbidden"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED,"Method not allowed"),
    NO_ROUTE_PERMISSION_DEFINED(HttpStatus.FORBIDDEN,"No route permission defined"),
    INSUFFICIENT_PERMISSIONS(HttpStatus.FORBIDDEN, "Insufficient permissions")
    ;

    private final HttpStatus status;
    private final String message;




}
