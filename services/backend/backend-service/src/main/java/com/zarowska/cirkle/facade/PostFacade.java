package com.zarowska.cirkle.facade;

import com.zarowska.cirkle.api.model.CreatePostRequest;
import com.zarowska.cirkle.api.model.Post;
import com.zarowska.cirkle.api.model.PostsPage;
import com.zarowska.cirkle.api.model.UpdatePostRequest;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

public interface PostFacade {

	@Transactional
	Post createPost(UUID userId, CreatePostRequest createPostRequest);

	@Transactional
	Post getUserPostByPostId(UUID userId, UUID postId);

	@Transactional
	Post updatePostById(UUID userId, UUID postId, UpdatePostRequest updatePostRequest);

	@Transactional
	PostsPage listPostsByUserId(UUID userId, Integer page, Integer size);
}
