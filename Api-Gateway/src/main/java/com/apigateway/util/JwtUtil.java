package com.apigateway.util;

import com.apigateway.config.SecurityProperties;
import com.apigateway.exception.AuthException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final SecurityProperties securityProperties;

    private Key getKey() {

        return Keys.hmacShaKeyFor(securityProperties.getJwtSecret().getBytes());

    }

    public Claims validateToken(String token) {

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (ExpiredJwtException e) {

            throw AuthException.tokenExpired(e);
        }
        catch (SignatureException e) {

            throw AuthException.invalidSignature(e);
        }
        catch (MalformedJwtException e) {

            throw AuthException.invalidTokenFormat(e);
        }
        catch (JwtException  | IllegalArgumentException e) {

            throw AuthException.invalidToken(e);
        }

    }

}
