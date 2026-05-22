package com.apigateway.handler;

import com.apigateway.constants.NumberConstants;
import com.apigateway.constants.SecurityConstants;
import com.apigateway.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitResponseHandler {


    private final ObjectMapper objectMapper;

    public Mono<Void> handle(ServerWebExchange exchange, ConsumptionProbe probe, String message,String key) {

        log.warn(message,key);

        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);

        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        exchange.getResponse().getHeaders().set(
                SecurityConstants.HEADER_RETRY_AFTER,
                String.valueOf(probe.getNanosToWaitForRefill() / NumberConstants.MAX_DAILY_REQUEST_CAPACITY)
        );

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.TOO_MANY_REQUESTS.value())
                .message(message.replace("{}",key))
                .path(exchange.getRequest().getURI().getPath())
                .timestamp(LocalDateTime.now())
                .build();

        return write(exchange, response);
    }

    private Mono<Void> write(ServerWebExchange exchange, Object body) {

        try {

            byte[] bytes = objectMapper.writeValueAsString(body)
                    .getBytes(StandardCharsets.UTF_8);

            DataBuffer buffer = exchange.getResponse()
                    .bufferFactory()
                    .wrap(bytes);

            return exchange.getResponse().writeWith(Mono.just(buffer));

        } catch (Exception ex) {

            return Mono.error(ex);

        }
    }

}
