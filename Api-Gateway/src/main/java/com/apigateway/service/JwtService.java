package com.apigateway.service;


import com.apigateway.config.SecurityProperties;
import com.apigateway.constants.SecurityConstants;
import com.apigateway.exception.AuthException;
import com.apigateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtUtil jwtUtil;
    private final SecurityProperties securityConfigurationProperties;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public String extractToken(ServerWebExchange exchange) {


        String header = exchange.getRequest()
                        .getHeaders()
                        .getFirst(SecurityConstants.AUTH_HEADER);

        if (!StringUtils.hasText(header) ||
                !header.startsWith(SecurityConstants.BEARER_PREFIX)) {

            throw AuthException.missingHeader();
        }

        return header.substring(SecurityConstants.BEARER_PREFIX.length());
    }

    public Claims decodeClaims(String token) {

        try {

            return Jwts.parserBuilder()
                    .build()
                    .parseClaimsJwt(token.substring(0, token.lastIndexOf('.') + 1))
                    .getBody();

        } catch (Exception e) {

            return null;
        }
    }

    public Claims validateToken(String token) {

        return jwtUtil.validateToken(token);

    }

    public String extractUserId(Claims claims) {

        Object userId = claims.get(SecurityConstants.CLAIM_USER_ID);

        return userId == null ? null : userId.toString();
    }

    public boolean isPublic(String path) {

        return securityConfigurationProperties.getPublicPaths()
                .stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, path));

    }

}
