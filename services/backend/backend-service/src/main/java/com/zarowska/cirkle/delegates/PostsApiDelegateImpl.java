package com.zarowska.cirkle.delegates;

import com.zarowska.cirkle.api.model.CreatePostRequest;
import com.zarowska.cirkle.api.model.Post;
import com.zarowska.cirkle.api.model.PostsPage;
import com.zarowska.cirkle.api.model.UpdatePostRequest;
import com.zarowska.cirkle.api.rest.PostsApiDelegate;
import com.zarowska.cirkle.facade.PostFacade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostsApiDelegateImpl implements PostsApiDelegate {

	private final PostFacade postFacade;

	@Override
	public ResponseEntity<Post> createPost(String userId, CreatePostRequest createPostRequest) {
		return ResponseEntity.ok(postFacade.createPost(userId, createPostRequest));
	}

	@Override
	public ResponseEntity<Void> deleteUserPostById(String userId, String postId) {
		return PostsApiDelegate.super.deleteUserPostById(userId, postId);
	}

	@Override
	public ResponseEntity<Post> getUserPostById(String userId, String postId) {
		return PostsApiDelegate.super.getUserPostById(userId, postId);
	}

	@Override
	public ResponseEntity<PostsPage> listUsersPostsByUserId(String userId, Integer page, Integer size) {
		return PostsApiDelegate.super.listUsersPostsByUserId(userId, page, size);
	}

	@Override
	public ResponseEntity<Post> updateUserPostById(String userId, String postId, UpdatePostRequest updatePostRequest) {
		return PostsApiDelegate.super.updateUserPostById(userId, postId, updatePostRequest);
	}
}
