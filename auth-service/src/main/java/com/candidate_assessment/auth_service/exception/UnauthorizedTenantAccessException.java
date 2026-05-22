package com.candidate_assessment.auth_service.exception;

public class UnauthorizedTenantAccessException extends RuntimeException{

    public UnauthorizedTenantAccessException(String message){

        super(message);

    }

}
