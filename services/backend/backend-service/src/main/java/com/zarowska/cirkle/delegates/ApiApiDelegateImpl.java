package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.BuildInfo;
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
		return ResponseEntity.ok(
				new ApiInfo().version(BuildInfo.VERSION).buildDate(BuildInfo.BUILD_DATE).buildNum(BuildInfo.BUILD_NUM));
	}
}
