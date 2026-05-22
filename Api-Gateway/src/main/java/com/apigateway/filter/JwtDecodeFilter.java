package com.apigateway.filter;

import com.apigateway.constants.FilterOrderConstants;
import com.apigateway.constants.SecurityConstants;
import com.apigateway.service.JwtService;
import io.jsonwebtoken.Claims;
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
public class JwtDecodeFilter implements GlobalFilter , Ordered {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        try {

//            String path = exchange.getRequest().getURI().getPath();
//
//            if(jwtService.isPublic(path))
//                return chain.filter(exchange);

            String token = jwtService.extractToken(exchange);

            Claims claims = jwtService.decodeClaims(token);

            if (claims != null) {

                String userId = jwtService.extractUserId(claims);

                if (userId != null) {

                    exchange.getAttributes().put(SecurityConstants.CLAIM_USER_ID, userId);
                }
            }

        } catch (RuntimeException ignored) {



        }

        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return FilterOrderConstants.JWT_DECODE;
    }
}
