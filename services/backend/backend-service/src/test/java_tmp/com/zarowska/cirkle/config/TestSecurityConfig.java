package com.zarowska.cirkle.config;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;

import java.text.ParseException;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

@Configuration
@RequiredArgsConstructor
public class TestSecurityConfig {

    private final MockJwtIssuer mockJwtIssuer;

    @Bean
    JwtDecoder mockDecoder() {
        return token -> {
            try {
                JWT parsed = JWTParser.parse(token);
                Jwt jwt = new Jwt(token, parsed.getJWTClaimsSet().getIssueTime().toInstant(),
                        parsed.getJWTClaimsSet().getExpirationTime().toInstant(), parsed.getHeader().toJSONObject(),
                        parsed.getJWTClaimsSet().getClaims());
                return jwt;
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
