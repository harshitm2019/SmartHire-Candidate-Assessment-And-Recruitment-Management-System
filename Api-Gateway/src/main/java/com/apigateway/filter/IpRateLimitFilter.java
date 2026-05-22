package com.apigateway.filter;

import com.apigateway.config.RateLimitProperties;
import com.apigateway.constants.FilterOrderConstants;
import com.apigateway.constants.LogConstants;
import com.apigateway.constants.SecurityConstants;
import com.apigateway.handler.RateLimitResponseHandler;
import com.apigateway.service.RateLimitService;
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

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class IpRateLimitFilter implements GlobalFilter , Ordered {

    private final RateLimitProcessUtil  processUtil;
    private final RateLimitProperties properties;
    private final RateLimitResponseHandler responseHandler;



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String ip = Objects.requireNonNull(exchange.getRequest()
                           .getRemoteAddress())
                           .getAddress()
                           .getHostAddress();

        String key = SecurityConstants.IP_PREFIX + ip;


        ConsumptionProbe probe = processUtil.handleRateLimit(
                key,
                properties.getIp().getCapacity(),
                properties.getIp().getRefillTokens(),
                properties.getIp().getRefillDuration()
        );


        if (!probe.isConsumed()) {

            return responseHandler.handle(
                    exchange,
                    probe,
                    LogConstants.IP_RATE_LIMIT_EXCEEDED,
                    key
            );
        }

        exchange.getResponse()
                .getHeaders()
                .set(
                        SecurityConstants.HEADER_REMAINING,
                        String.valueOf(probe.getRemainingTokens())
                );

         return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return FilterOrderConstants.IP_RATE_LIMITER;
    }
}
