package com.zarowska.cirkle.api;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;

public interface CustomJwtConverter extends Converter<Jwt, AbstractAuthenticationToken> {
}
