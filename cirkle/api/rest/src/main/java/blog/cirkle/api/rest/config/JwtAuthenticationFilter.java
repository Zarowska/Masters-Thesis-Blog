package blog.cirkle.api.rest.config;

import blog.cirkle.api.rest.controller.AuthController;
import blog.cirkle.domain.facade.AuthFacade;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final AuthFacade authFacade;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
				.filter(it -> it.startsWith(AuthController.BEARER_PREFIX)
						&& it.length() > AuthController.BEARER_PREFIX.length())
				.map(it -> it.substring(AuthController.BEARER_PREFIX.length())).ifPresent(token -> {
					try {
						authFacade.authenticateByToken(token);
					} catch (Exception e) {
						// ignore
					}
				});

		filterChain.doFilter(request, response);
	}
}
