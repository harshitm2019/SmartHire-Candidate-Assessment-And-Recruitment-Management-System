package com.apigateway.filter;

import com.apigateway.config.RateLimitProperties;
import com.apigateway.constants.FilterOrderConstants;
import com.apigateway.constants.LogConstants;
import com.apigateway.constants.SecurityConstants;
import com.apigateway.handler.RateLimitResponseHandler;
import com.apigateway.service.JwtService;
import com.apigateway.util.RateLimitProcessUtil;
import io.github.bucket4j.ConsumptionProbe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRateLimitFilter implements GlobalFilter, Ordered {


    private final RateLimitProperties properties;
    private final JwtService jwtService;
    private final RateLimitProcessUtil processUtil;
    private final RateLimitResponseHandler responseHandler;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        if(jwtService.isPublic(path))
            return chain.filter(exchange);

        String userId = exchange.getAttribute(SecurityConstants.CLAIM_USER_ID);

        if (userId == null) {
            return chain.filter(exchange);
        }


        String key = SecurityConstants.USER_PREFIX + userId;

        ConsumptionProbe probe =  processUtil.handleRateLimit(

                        key,
                        properties.getUser().getCapacity(),
                        properties.getUser().getRefillTokens(),
                        properties.getUser().getRefillDuration()

                );

        if(!probe.isConsumed()){

            return responseHandler.handle(

                    exchange,
                    probe,
                    LogConstants.USER_RATE_LIMIT_EXCEEDED,
                    userId

            );
        }

        exchange.getResponse()
                .getHeaders()
                .set(
                        SecurityConstants.HEADER_USER_REMAINING,
                        String.valueOf(probe.getRemainingTokens())
                );

        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return FilterOrderConstants.USER_RATE_LIMITER;
    }
}
