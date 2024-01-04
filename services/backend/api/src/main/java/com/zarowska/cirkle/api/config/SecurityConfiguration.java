package com.zarowska.cirkle.api.config;

import com.zarowska.cirkle.api.CustomJwtConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final CustomJwtConverter jwtConverter;
	private final CustomAuthenticationEntryPoint entryPoint;

	@Bean
	SecurityFilterChain setupResourceServer(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.authorizeHttpRequests(http -> http.requestMatchers(HttpMethod.GET, "/favicon.ico").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/info").permitAll()
						.requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
						.requestMatchers(HttpMethod.GET, "/openapi/**", "/swagger-ui/**", "/api-docs/**").permitAll()
						.anyRequest().authenticated())
				.oauth2ResourceServer(oauth -> oauth.authenticationEntryPoint(entryPoint)
						.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
				.build();
	}
}