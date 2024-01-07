package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.CreatePostRequest;
import com.zarowska.cirkle.api.model.Post;
import com.zarowska.cirkle.api.model.PostsPage;
import com.zarowska.cirkle.api.model.UpdatePostRequest;
import com.zarowska.cirkle.api.rest.PostsApiDelegate;
import com.zarowska.cirkle.facade.PostFacade;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostsApiDelegateImpl implements PostsApiDelegate {

	private final PostFacade postFacade;

	@Override
	public ResponseEntity<Post> createPost(UUID userId, CreatePostRequest createPostRequest) {
		return ResponseEntity.ok(postFacade.createPost(userId, createPostRequest));
	}

	@Override
	public ResponseEntity<Void> deleteUserPostById(UUID userId, UUID postId) {
		return PostsApiDelegate.super.deleteUserPostById(userId, postId);
	}

	@Override
	public ResponseEntity<Post> getUserPostById(UUID userId, UUID postId) {
		return PostsApiDelegate.super.getUserPostById(userId, postId);
	}

	@Override
	public ResponseEntity<PostsPage> listUsersPostsByUserId(UUID userId, Integer page, Integer size) {
		return PostsApiDelegate.super.listUsersPostsByUserId(userId, page, size);
	}

	@Override
	public ResponseEntity<Post> updateUserPostById(UUID userId, UUID postId, UpdatePostRequest updatePostRequest) {
		return PostsApiDelegate.super.updateUserPostById(userId, postId, updatePostRequest);
	}
}
