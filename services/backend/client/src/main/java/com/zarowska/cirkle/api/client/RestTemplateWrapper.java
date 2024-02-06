package com.zarowska.cirkle.api.client;

import com.zarowska.cirkle.api.model.ApiError;
import com.zarowska.cirkle.exception.CirkleException;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class RestTemplateWrapper {

	private final RestTemplate restTemplate = new RestTemplate();
	private final String baseUrl;

	@Setter
	private String bearerToken;

	public RestTemplateWrapper(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(bearerToken);
		return headers;
	}

	public <T> ResponseEntity<T> get(Class<T> outClass, String url, Object... params) {
		return exchange(HttpMethod.GET, null, outClass, url, params);
	}

	public <IN, OUT> ResponseEntity<OUT> post(IN body, Class<OUT> outClass, String url, Object... params) {
		return exchange(HttpMethod.POST, body, outClass, url, params);
	}

	public <OUT> ResponseEntity<OUT> postForm(MultiValueMap<String, Object> body, Class<OUT> outClass, String url,
			Object... params) {
		HttpHeaders headers = createHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		return exchange(HttpMethod.POST, body, outClass, url, headers, params);
	}

	public <IN, OUT> ResponseEntity<OUT> put(IN body, Class<OUT> outClass, String url, Object... params) {
		return exchange(HttpMethod.PUT, body, outClass, url, params);
	}

	public <T> ResponseEntity<T> delete(Class<T> outClass, String url, Object... params) {
		return exchange(HttpMethod.DELETE, null, outClass, url, params);
	}

	private <IN, OUT> ResponseEntity<OUT> exchange(HttpMethod method, IN body, Class<OUT> outClass, String url,
			Object... params) {
		return exchange(method, body, outClass, url, createHeaders(), params);
	}

	private <IN, OUT> ResponseEntity<OUT> exchange(HttpMethod method, IN body, Class<OUT> outClass, String url,
			MultiValueMap<String, String> headers, Object... params) {
		try {
			HttpEntity<IN> httpEntity = new HttpEntity<>(body, headers);
			return restTemplate.exchange(baseUrl + url, method, httpEntity, outClass, params);
		} catch (HttpClientErrorException.NotFound e) {
			return null;
		} catch (HttpClientErrorException e) {
			try {
				ApiError apiError = e.getResponseBodyAs(ApiError.class);
				throw new CirkleException(apiError.getError().getDetail(),
						HttpStatus.resolve(e.getStatusCode().value()));
			} catch (RestClientException e1) {
				throw new RuntimeException(e);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
