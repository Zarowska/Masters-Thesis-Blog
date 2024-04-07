package com.zarowska.cirkle.api;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.*;
import com.zarowska.cirkle.utils.TestUserContext;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostCommentsApiTest extends AbstractTest {

	// Get post's comment
	// done // Create a post comment
	// Get post's comment
	// Get post's comment by commentId
	// Delete post's comment
	// Update post's comment

	TestUserContext testUserContext;

	@BeforeEach
	void setUp() {
		testUserContext = context("Bob Marley", "bob@marley.com", "http:/some/avatar");
		testUserContext.getApi().api().apiInfo(); // Simulating API call to setup user
	}

	@Test
	void shouldCreatePostComment_ShouldSucceed() {

		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> testUserContext.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();
		CreatePostRequest postRequest = CreatePostRequest.builder().text("New post").images(imagesListURI).build();
		Optional<Post> newPost = testUserContext.getApi().posts().createPost(testUserContext.getUserId(), postRequest);
		Assert.assertTrue(newPost.isPresent());
		assertEquals("New post", newPost.get().getText());

		CreateCommentRequest commentRequest = CreateCommentRequest.builder().text("New comment")
				.images(Collections.emptyList()).build();

		Optional<Comment> newComment = testUserContext.getApi().posts().comments()
				.addPostComment(testUserContext.getUserId(), newPost.get().getId(), commentRequest);
		assertTrue(newComment.isPresent());

	}

	// Get post's comment by commentId
	@Test
	void shouldGetPostComment() throws Exception {
		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> testUserContext.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();
		CreatePostRequest postRequest = CreatePostRequest.builder().text("New post").images(imagesListURI).build();
		Optional<Post> newPost = testUserContext.getApi().posts().createPost(testUserContext.getUserId(), postRequest);
		Assert.assertTrue(newPost.isPresent());
		assertEquals("New post", newPost.get().getText());

		CreateCommentRequest commentRequest = CreateCommentRequest.builder().text("New comment")
				.images(Collections.emptyList()).build();

		Optional<Comment> newComment = testUserContext.getApi().posts().comments()
				.addPostComment(testUserContext.getUserId(), newPost.get().getId(), commentRequest);
		assertTrue(newComment.isPresent());

		UUID commentId = newComment.get().getId();

		Optional<Comment> commentById = testUserContext.getApi().posts().comments()
				.getPostCommentById(testUserContext.getUserId(), newPost.get().getId(), commentId);

		assertTrue(commentById.isPresent());

		assertEquals("New comment", commentById.get().getText());

	}

}
