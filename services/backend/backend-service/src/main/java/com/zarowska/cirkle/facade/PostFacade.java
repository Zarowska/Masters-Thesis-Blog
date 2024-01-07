package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.CreatePostRequest;
import com.zarowska.cirkle.api.model.Post;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface PostFacade {

	@Transactional
	Post createPost(UUID userId, CreatePostRequest createPostRequest);
}
