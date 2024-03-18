package com.zarowska.cirkle.security;

import java.util.Optional;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

	public static BlogUser getCurrentUser() {
		return Optional.ofNullable(SecurityContextHolder.getContext()).map(SecurityContext::getAuthentication)
				.filter(BlogUser.class::isInstance).map(it -> (BlogUser) it).orElseThrow();
	}

}
