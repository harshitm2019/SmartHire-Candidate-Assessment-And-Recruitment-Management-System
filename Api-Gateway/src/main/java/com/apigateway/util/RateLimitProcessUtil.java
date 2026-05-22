package com.apigateway.util;

import com.apigateway.constants.SecurityConstants;
import com.apigateway.handler.RateLimitResponseHandler;
import com.apigateway.service.RateLimitService;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class RateLimitProcessUtil {


    private final RateLimitService rateLimitService;

    public ConsumptionProbe handleRateLimit(
            String key,
            long capacity,
            long refillTokens,
            long refillDuration

    ) {

        return rateLimitService.consume(
                key,
                capacity,
                refillTokens,
                refillDuration
        );



    }


}
