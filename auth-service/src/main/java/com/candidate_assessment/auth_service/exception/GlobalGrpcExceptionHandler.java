package com.candidate_assessment.auth_service.exception;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import jakarta.servlet.http.HttpServletRequest;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

@GrpcAdvice
public class GlobalGrpcExceptionHandler {

    @GrpcExceptionHandler(UserAlreadyExistsException.class)
    public StatusRuntimeException handleInvalidCredentials(
            UserAlreadyExistsException ex
    ) {

        return Status.ALREADY_EXISTS
                .withDescription(ex.getMessage())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(RoleNotFoundException.class)
    public StatusRuntimeException handleGeneral(
            RoleNotFoundException ex
    ) {

        return Status.NOT_FOUND
                .withDescription(ex.getMessage())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(MethodArgumentNotValidException.class)
    public StatusRuntimeException handleValidation(
            MethodArgumentNotValidException ex
    ){


        return Status.INVALID_ARGUMENT
                .withDescription(ex.getMessage())
                .asRuntimeException();

    }

    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleException(Exception ex) {

        return Status.INTERNAL
                .withDescription(ex.getMessage())
                .asRuntimeException();

    }

}
