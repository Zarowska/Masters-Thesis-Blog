package com.zarowska.cirkle.api.client.endpoints;

import com.zarowska.cirkle.api.client.RestTemplateWrapper;
import com.zarowska.cirkle.api.model.ApiInfo;

public class ApiEndpoint extends AbstractClientEndpoint {

	public ApiEndpoint(RestTemplateWrapper restTemplateWrapper) {
		super(restTemplateWrapper);
	}

	public ApiInfo apiInfo() {
		return restTemplateWrapper.get(ApiInfo.class, "/info").getBody();
	}
}
