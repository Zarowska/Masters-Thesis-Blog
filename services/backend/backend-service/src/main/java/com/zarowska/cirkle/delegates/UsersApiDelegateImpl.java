package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.Profile;
import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.api.model.UserPage;
import com.zarowska.cirkle.api.rest.UsersApiDelegate;
import com.zarowska.cirkle.facade.UsersFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersApiDelegateImpl implements UsersApiDelegate {

	private final UsersFacade usersFacade;

	@Override
	public ResponseEntity<User> getUserById(String userId) {
		return UsersApiDelegate.super.getUserById(userId);
	}

	@Override
	public ResponseEntity<Profile> getUsersProfileById(String userId) {
		return ResponseEntity.ok(usersFacade.getProfileById(userId));
	}

	@Override
	public ResponseEntity<UserPage> listUsers() {
		return UsersApiDelegate.super.listUsers();
	}
}
