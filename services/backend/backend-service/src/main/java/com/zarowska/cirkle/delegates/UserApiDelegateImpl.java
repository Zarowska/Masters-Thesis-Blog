package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.api.rest.UserApiDelegate;
import com.zarowska.cirkle.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserApiDelegateImpl implements UserApiDelegate {

	private final UserFacade userFacade;

	@Override
	public ResponseEntity<User> getCurrentUser() {
		return ResponseEntity.ok(userFacade.getCurrentUser());
	}
}
