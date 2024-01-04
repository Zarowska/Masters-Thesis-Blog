package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.ApiInfo;
import com.zarowska.cirkle.api.rest.ApiApiDelegate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApiApiDelegateImpl implements ApiApiDelegate {

	@Override
	public ResponseEntity<ApiInfo> apiInfo() {
		return ApiApiDelegate.super.apiInfo();
	}
}
