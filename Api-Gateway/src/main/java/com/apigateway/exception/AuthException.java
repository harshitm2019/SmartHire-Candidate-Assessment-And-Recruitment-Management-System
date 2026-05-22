package com.apigateway.exception;

import com.apigateway.enums.AuthError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthException extends ApiException {


    private AuthException(AuthError authError) {

        super(authError.getStatus(), authError.getMessage());

    }

    public static AuthException tokenExpired(ExpiredJwtException e) {

        log.warn("{}: {}", AuthError.TOKEN_EXPIRED.getMessage(), e.getMessage());
        return new AuthException(AuthError.TOKEN_EXPIRED);

    }

    public static AuthException invalidToken(RuntimeException e) {

        log.warn("{}: {}", AuthError.INVALID_TOKEN.getMessage(), e.getMessage());
        return new AuthException(AuthError.INVALID_TOKEN);

    }

    public static AuthException invalidSignature(SignatureException e) {

        log.warn("{}: {}", AuthError.INVALID_SIGNATURE.getMessage(), e.getMessage());
        return new AuthException(AuthError.INVALID_SIGNATURE);

    }

    public static AuthException invalidTokenFormat(MalformedJwtException e) {

        log.warn("{}: {}", AuthError.INVALID_TOKEN_FORMAT.getMessage(), e.getMessage());
        return new AuthException(AuthError.INVALID_TOKEN_FORMAT);

    }

    public static AuthException missingHeader() {

        log.warn("{}", AuthError.MISSING_HEADER.getMessage());
        return new AuthException(AuthError.MISSING_HEADER);

    }

    public static AuthException forbidden() {

        log.warn("{}", AuthError.FORBIDDEN.getMessage());
        return new AuthException(AuthError.FORBIDDEN);

    }

    public static AuthException methodNotAllowed() {

        log.warn("{}", AuthError.METHOD_NOT_ALLOWED.getMessage());
        return new AuthException(AuthError.METHOD_NOT_ALLOWED);

    }

    public static AuthException noRoutePermissionDefined() {

        log.warn("{}",AuthError.NO_ROUTE_PERMISSION_DEFINED.getMessage());
        return new AuthException(AuthError.NO_ROUTE_PERMISSION_DEFINED);

    }

    public static AuthException insufficientPermissions() {

        log.warn("{}",AuthError.INSUFFICIENT_PERMISSIONS.getMessage());
        return new AuthException(AuthError.INSUFFICIENT_PERMISSIONS);

    }
}