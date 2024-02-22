package com.zarowska.cirkle.api;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.*;
import com.zarowska.cirkle.exception.CirkleException;
import com.zarowska.cirkle.utils.TestUserContext;
import java.net.URI;
import java.util.*;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

class PostApiTest extends AbstractTest {

	// done // Get user's posts
	// done // Create a post
	// done // Get user's post by post id
	// done // Update post by id
	// done // Delete post by id

	TestUserContext testUserContext;

	@BeforeEach
	void setUp() {
		testUserContext = context("Bob Marley", "bob@marley.com", "http:/some/avatar");
		testUserContext.getApi().api().apiInfo(); // Simulating API call to setup user
	}

	@Test
	void shouldCreatePost() {
		CreatePostRequest request = CreatePostRequest.builder().text("New post").images(Collections.emptyList())
				.build();
		Optional<Post> newPost = testUserContext.getApi().posts().createPost(testUserContext.getUserId(), request);
		assertTrue(newPost.isPresent());
	}

	@Test
	void shouldCreatePostWithPhotos() {
		List<URI> imagesListURI = Stream.of("max_payne.png", "blazkovic.png")
				.map(it -> getFileFromResource("files/" + it))
				.map(imageResource -> testUserContext.getApi().images().uploadImage(imageResource)).map(FileDto::getUrl)
				.toList();
		CreatePostRequest request = CreatePostRequest.builder().text("New post").images(imagesListURI).build();
		Optional<Post> newPost = testUserContext.getApi().posts().createPost(testUserContext.getUserId(), request);
		assertTrue(newPost.isPresent());
		assertEquals("New post", newPost.get().getText());
		assertNotNull(newPost.get().getImages());
		assertEquals(imagesListURI, newPost.get().getImages());
	}

	@Test
	void getUserPostByPostId_ShouldSucceed() {
		CreatePostRequest request = CreatePostRequest.builder().text("New post").images(Collections.emptyList())
				.build();
		UUID postId = testUserContext.getApi().posts().createPost(testUserContext.getUserId(), request).get().getId();
		assertNotNull(String.valueOf(postId), "Post ID should not be null");
		Optional<Post> thisPost = testUserContext.getApi().posts().getUserPostByPostId(testUserContext.getUserId(),
				postId);
		assertThat(thisPost).isNotEmpty();
	}

	@Test
	void updatePost_ShouldSucceed() {
		CreatePostRequest request = CreatePostRequest.builder().text("Old text").images(Collections.emptyList())
				.build();
		Post post = testUserContext.getApi().posts().createPost(testUserContext.getUserId(), request).get();
		assertEquals("Old text", post.getText());

		UpdatePostRequest updateTextRequest = new UpdatePostRequest().text("New post");
		Post updatedPost = testUserContext.getApi().posts()
				.updateUserPostById(testUserContext.getUserId(), post.getId(), updateTextRequest).get();
		assertEquals("New post", updatedPost.getText());

		Resource imageResource = getFileFromResource("files/max_payne.png");
		FileDto response = testUserContext.getApi().images().uploadImage(imageResource);

		assertEquals(Collections.emptyList(), updatedPost.getImages());
		UpdatePostRequest updateImagesItemRequest = new UpdatePostRequest().addImagesItem(response.getUrl());
		updatedPost = testUserContext.getApi().posts()
				.updateUserPostById(testUserContext.getUserId(), post.getId(), updateImagesItemRequest).get(); // addImagesItemRequest
		List<String> oneImage = updatedPost.getImages().stream().map(URI::getPath).toList();
		assertEquals(1, oneImage.size());

		// UpdatePostRequest updateImagesItemRequest2 = new
		// UpdatePostRequest().addImagesItem(null);
		// updatedPost = testUserContext.getApi().posts()
		// .updateUserPostById(testUserContext.getUserId(), post.getId(),
		// updateImagesItemRequest2).get(); //deleteImagesItemRequest
		// List<String> zeroImage =
		// updatedPost.getImages().stream().map(URI::getPath).toList();
		// assertEquals(0,zeroImage.size());

		// TODO
		// assertThat(oneImage).containsAll(Collections.singleton("files/max_payne.png"));
		// updatedPost = testUserContext.getApi().posts()
		// .updateUserPostById(testUserContext.getUserId(), post.getId(),
		// updateImagesItemRequest).get();

	}

	@Test
	void updatePost_ShouldThrowException() throws Exception {
		CreatePostRequest requestCreatePostRequest = CreatePostRequest.builder().text("Old text")
				.images(Collections.emptyList()).build();
		Optional<Post> thisPost = testUserContext.getApi().posts().createPost(testUserContext.getUserId(),
				requestCreatePostRequest);
		assertThat(thisPost).isNotEmpty();
		context("Max Payne", "max@email", "http://avatar2").apply(maxContext -> {
			Exception exception = assertThrows(CirkleException.class, () -> {
				maxContext.getApi().posts().updateUserPostById(maxContext.getUserId(), thisPost.get().getId(),
						new UpdatePostRequest().text("New post"));
			});
			String expectedMessage = "Only the original author of this post has permission to make updates";
			assertEquals(expectedMessage, exception.getMessage());
		});
	}

	@Test
	void listPost_ShouldSucceed() {
		List<String> expectedPostText = List.of("Post1", "Post2", "Post3", "Post4");
		expectedPostText.stream().map(text -> testUserContext.getApi().posts().createPost(testUserContext.getUserId(),
				new CreatePostRequest().text(text))).map(Optional::get).toList();
		Optional<PostsPage> postsPage = testUserContext.getApi().posts()
				.listUsersPostsByUserId(testUserContext.getUserId());
		List<String> allPostText = postsPage.get().getContent().stream().map(Post::getText).toList();

		assertThat(allPostText).containsAll(expectedPostText);

	}

	@Test
	void deletePost_ShouldSucceed() throws Exception {
		CreatePostRequest request = CreatePostRequest.builder().text("New post").images(Collections.emptyList())
				.build();
		Optional<Post> newPost = testUserContext.getApi().posts().createPost(testUserContext.getUserId(), request);
		assertTrue(newPost.isPresent());
		UUID postId = newPost.get().getId();
		testUserContext.getApi().posts().deleteUserPostById(testUserContext.getUserId(), postId);
		Exception exception = assertThrows(CirkleException.class, () -> {
			testUserContext.getApi().posts().getUserPostByPostId(testUserContext.getUserId(), postId);
		});
		String expectedMessage = "Post not found with id=" + postId.toString();
		assertEquals(expectedMessage, exception.getMessage());
	}

	@Test
	void deletePost_ThrowException() throws Exception {
		CreatePostRequest request = CreatePostRequest.builder().text("New post").images(Collections.emptyList())
				.build();
		Optional<Post> newPost = testUserContext.getApi().posts().createPost(testUserContext.getUserId(), request);
		assertTrue(newPost.isPresent());
		UUID postId = newPost.get().getId();
		context("Max Payne", "max@email", "http://avatar2").apply(maxContext -> {
			Exception exception = assertThrows(CirkleException.class, () -> {
				maxContext.getApi().posts().deleteUserPostById(maxContext.getUserId(), postId);
			});
			String expectedMessage = "Only the original author of this post has permission to delete it";
			assertEquals(expectedMessage, exception.getMessage());
		});
	}

}
