package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.PostsPage;
import com.zarowska.cirkle.api.rest.UserFeedApiDelegate;
import com.zarowska.cirkle.facade.PostFacade;
import com.zarowska.cirkle.facade.UserFacade;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFeedApiDelegateImpl implements UserFeedApiDelegate {

	private final PostFacade postFacade;
	private final UserFacade userFacade;

	@Override
	public ResponseEntity<PostsPage> getFeed(Integer page, Integer size) {
		int pageNumber = Optional.ofNullable(page).orElse(0);
		int pageSize = Optional.ofNullable(size).orElse(20);
		return ResponseEntity
				.ok(postFacade.listPostsByUserId(userFacade.getCurrentUser().getId(), pageNumber, pageSize));
	}
}
