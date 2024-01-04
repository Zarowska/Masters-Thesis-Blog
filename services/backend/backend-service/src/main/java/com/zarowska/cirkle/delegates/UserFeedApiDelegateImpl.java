package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.PostsPage;
import com.zarowska.cirkle.api.rest.UserFeedApiDelegate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFeedApiDelegateImpl implements UserFeedApiDelegate {

	@Override
	public ResponseEntity<PostsPage> getFeed(Integer page, Integer size) {
		return UserFeedApiDelegate.super.getFeed(page, size);
	}
}
