package com.candidate_assessment.auth_service.exception;

import com.candidate_assessment.auth_service.constants.ErrorConstants;
import com.candidate_assessment.auth_service.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(Exception ex, HttpServletRequest req) {

        return createResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), req);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(Exception ex, HttpServletRequest req) {

         return createResponse(HttpStatus.NOT_FOUND, ex.getMessage(), req);

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req
    ) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(ErrorConstants.VALIDATION_FAILED);

        return createResponse(HttpStatus.BAD_REQUEST, message, req
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(Exception ex, HttpServletRequest req) {

        return createResponse(HttpStatus.CONFLICT, ex.getMessage(), req);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest req) {

        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorConstants.INTERNAL_SERVER_ERROR, req);

    }

    private ResponseEntity<ErrorResponse> createResponse(HttpStatus status, String msg, HttpServletRequest req) {

        ErrorResponse body = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(msg)
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(body);
    }
}
