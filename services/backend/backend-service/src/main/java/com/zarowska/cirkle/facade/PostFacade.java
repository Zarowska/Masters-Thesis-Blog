package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.CreatePostRequest;
import com.zarowska.cirkle.api.model.Post;
import org.springframework.transaction.annotation.Transactional;

public interface PostFacade {

	@Transactional
	Post createPost(String userId, CreatePostRequest createPostRequest);
}
