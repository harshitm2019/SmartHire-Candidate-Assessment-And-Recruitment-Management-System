package com.apigateway.exception;

import com.apigateway.constants.ApiConstants;
import com.apigateway.constants.JsonConstants;
import com.apigateway.constants.LogConstants;
import com.apigateway.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@Order(-2)
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        String path = exchange.getRequest().getURI().getPath();

        String method = exchange.getRequest().getMethod().name();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;


        String message = ApiConstants.DEFAULT_ERR_MSG;

        if(ex instanceof ApiException apiException){

            status = apiException.getStatus();
            message = apiException.getMessage();

        }

        if (status.is5xxServerError()) {

            log.error(LogConstants.LOG_INTERNAL_ERROR, path,method,ex);

        } else {

            log.warn(LogConstants.LOG_CLIENT_ERROR, path, method,message);

        }


        ErrorResponse errorResponse = ErrorResponse.builder()
                                                   .path(path)
                                                   .message(message)
                                                   .timestamp(LocalDateTime.now())
                                                   .status(status.value())
                                                   .build();


        byte[] body;
        try {
            body = objectMapper.writeValueAsBytes(errorResponse);
        } catch (Exception e) {

            body = JsonConstants.FALLBACK_ERROR_JSON.getBytes(StandardCharsets.UTF_8);;

        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);


        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse()
                        .bufferFactory()
                        .wrap(body)));


    }
}
