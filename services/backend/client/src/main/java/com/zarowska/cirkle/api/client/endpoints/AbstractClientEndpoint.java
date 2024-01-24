package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
public abstract class AbstractClientEndpoint {
	protected final RestTemplateWrapper restTemplateWrapper;

	protected <T> Optional<T> doCall(Supplier<ResponseEntity<T>> action) {
		try {
			return Optional.ofNullable(action.get()).map(ResponseEntity::getBody);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
