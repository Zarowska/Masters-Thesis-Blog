package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.Profile;
import com.zarowska.cirkle.api.model.User;
import com.zarowska.cirkle.api.model.UserPage;
import com.zarowska.cirkle.api.rest.UsersApiDelegate;
import com.zarowska.cirkle.facade.UsersFacade;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsersApiDelegateImpl implements UsersApiDelegate {

	private final UsersFacade usersFacade;

	@Override
	public ResponseEntity<User> getUserById(UUID userId) {
		return UsersApiDelegate.super.getUserById(userId);
	}

	@Override
	public ResponseEntity<Profile> getUsersProfileById(UUID userId) {
		return ResponseEntity.ok(usersFacade.getProfileById(userId));
	}

	@Override
	public ResponseEntity<UserPage> listUsers(BigDecimal page, BigDecimal size) {
		int pageNumber = Optional.ofNullable(page).map(BigDecimal::intValue).orElse(0);
		int pageSize = Optional.ofNullable(size).map(BigDecimal::intValue).orElse(20);
		return ResponseEntity.ok(usersFacade.listUsers(PageRequest.of(pageNumber, pageSize)));
	}
}
