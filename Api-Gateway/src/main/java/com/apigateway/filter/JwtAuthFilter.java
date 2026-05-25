package com.apigateway.filter;


import com.apigateway.constants.FilterOrderConstants;
import com.apigateway.constants.SecurityConstants;
import com.apigateway.exception.AuthException;
import com.apigateway.service.AuthorizationService;
import com.apigateway.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements GlobalFilter , Ordered {



    private final AuthorizationService authorizationService;
    private  final JwtService jwtService;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {


        String path = exchange.getRequest().getURI().getPath();


        if (jwtService.isPublic(path)) {
            return chain.filter(exchange);
        }

        String token = jwtService.extractToken(exchange);

        String method = exchange.getRequest().getMethod().name();

        Claims claims = jwtService.validateToken(token);

        List<String> permissions = claims.get(SecurityConstants.CLAIM_PERMISSIONS, List.class);

        if(permissions == null)
            return Mono.error(AuthException.forbidden());

        authorizationService.validateAccess(path,method,permissions);

        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate();

        Object userId = claims.get(SecurityConstants.CLAIM_USER_ID);
        List<String> roles = claims.get(SecurityConstants.CLAIM_ROLE, List.class);


        if (userId != null) {
            requestBuilder.header(SecurityConstants.HEADER_USER_ID, userId.toString());
        }

        if (roles != null) {
            requestBuilder.header(SecurityConstants.HEADER_ROLES, String.join(",", roles));
        }


        requestBuilder.header(SecurityConstants.HEADER_PERMISSIONS, String.join(",", permissions));



        return chain.filter(exchange.mutate().request(requestBuilder.build()).build());


    }



    @Override
    public int getOrder() {

        return FilterOrderConstants.JWT_AUTHENTICATION;

    }
}

