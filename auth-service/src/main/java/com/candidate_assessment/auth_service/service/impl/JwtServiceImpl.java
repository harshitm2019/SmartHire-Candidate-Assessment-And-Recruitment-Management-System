package com.candidate_assessment.auth_service.service.impl;

import com.candidate_assessment.auth_service.config.JwtConfig;
import com.candidate_assessment.auth_service.constants.SecurityConstants;
import com.candidate_assessment.auth_service.dto.JwtUserContext;
import com.candidate_assessment.auth_service.service.JwtService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtConfig jwtConfig;

    @Override
    public String generateAccessToken(JwtUserContext context) {


        Instant now = Instant.now();

        Instant expiry = now.plusMillis(
                jwtConfig.getAccessTokenExpiration()
        );



            JwtBuilder jwtBuilder = Jwts.builder()
                    .id(UUID.randomUUID().toString())
                    .issuer(jwtConfig.getIssuer())
                    .subject(String.valueOf(context.getUserId()))
                    .issuedAt(Date.from(now))
                    .expiration(Date.from(expiry))
                    .claim(
                            SecurityConstants.USER_ID,
                            context.getUserId()
                    )
                    .claim(
                            SecurityConstants.EMAIL,
                            context.getEmail()
                    )
                    .claim(
                            SecurityConstants.ROLES,
                            context.getRoles()
                    )
                    .claim(
                            SecurityConstants.PERMISSIONS,
                            context.getPermissions()
                    )
                    .claim(
                            SecurityConstants.TOKEN_TYPE,
                            SecurityConstants.BEARER
                    );

        if (context.getCompanyId() != null) {

            jwtBuilder.claim(
                    SecurityConstants.COMPANY_ID,
                    context.getCompanyId()
            );
        }

        return jwtBuilder.signWith(getSigningKey(),Jwts.SIG.HS256)
                         .compact();


    }

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                jwtConfig.getSecretKey()
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

}
