package com.zarowska.cirkle.security;

import com.zarowska.cirkle.domain.entity.UserEntity;
import java.util.Set;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class BlogUser extends AbstractAuthenticationToken {

	private final Jwt credentials;

	private final UserEntity principal;

	public BlogUser(Jwt credentials, UserEntity principal) {
		super(Set.of(new SimpleGrantedAuthority("ROLE_USER")));
		this.credentials = credentials;
		this.principal = principal;
		setAuthenticated(true);
	}

	@Override
	public Jwt getCredentials() {
		return credentials;
	}

	@Override
	public UserEntity getPrincipal() {
		return principal;
	}
}
