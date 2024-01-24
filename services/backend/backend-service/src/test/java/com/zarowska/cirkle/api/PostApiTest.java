package com.zarowska.cirkle.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.*;
import com.zarowska.cirkle.exception.CirkleException;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PostApiTest extends AbstractTest {
	@Test
	void shouldCreatePost() throws Exception {
		context("Bob Marley", "bob@marley.com", "http:/some/avatar").apply(ctx -> {
			CreatePostRequest request = CreatePostRequest.builder().text("New post").images(Collections.emptyList())
					.build();
			Optional<Post> newPost = ctx.getApi().posts().createPost(ctx.getUserId(), request);
			assertTrue(newPost.isPresent());
		});
	}

	@Test
	void getUserPostByPostId_ShouldSucceed() throws Exception {
		context("Bob Marley", "bob@marley.com", "http:/some/avatar").apply(ctx -> {
			CreatePostRequest request = CreatePostRequest.builder().text("New post").images(Collections.emptyList())
					.build();
			UUID postId = ctx.getApi().posts().createPost(ctx.getUserId(), request).get().getId();
			assertNotNull(String.valueOf(postId), "Post ID should not be null");
			// assertEquals("1", String.valueOf(postId));
			Optional<Post> thisPost = ctx.getApi().posts().getUserPostByPostId(ctx.getUserId(), postId);
			assertThat(thisPost).isNotEmpty();
		});
	}

	@Test
	void updatePost_ShouldSucceed() throws Exception {
		context("Bob Marley", "bob@marley.com", "http:/some/avatar").apply(ctx -> {
			CreatePostRequest request = CreatePostRequest.builder().text("Old text").images(Collections.emptyList())
					.build();
			Post newPost = ctx.getApi().posts().createPost(ctx.getUserId(), request).get();
			assertEquals("Old text", newPost.getText());

			UpdatePostRequest updatePostRequest = new UpdatePostRequest().text("New post");
			Post updatedPost = ctx.getApi().posts()
					.updateUserPostById(ctx.getUserId(), newPost.getId(), updatePostRequest).get();

			assertEquals("New post", updatedPost.getText());

			Post postAfterUpdate = ctx.getApi().posts().getUserPostByPostId(ctx.getUserId(), updatedPost.getId()).get();

			assertEquals("New post", postAfterUpdate.getText());
		});
	}

	@Test
	void updatePost_ShouldThrowException() throws Exception {
		context("Bob Marley", "bob@email", "http://avatar").apply(bobContest -> {
			CreatePostRequest requestCreatePostRequest = CreatePostRequest.builder().text("Old text")
					.images(Collections.emptyList()).build();
			Optional<Post> thisPost = bobContest.getApi().posts().createPost(bobContest.getUserId(),
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
		});
	}

	@Test
	void listPost_ShouldSucceed() throws Exception {
		context("Bob Marley", "bob@marley.com", "http:/some/avatar").apply(ctx -> {
			List<String> expectedPostText = List.of("Post1", "Post2", "Post3", "Post4");
			// List<Post> createdPosts =
			expectedPostText.stream()
					.map(text -> ctx.getApi().posts().createPost(ctx.getUserId(), new CreatePostRequest().text(text)))
					.map(Optional::get).toList();
			Optional<PostsPage> postsPage = ctx.getApi().posts().listUsersPostsByUserId(ctx.getUserId());
			List<String> allPostText = postsPage.get().getContent().stream().map(Post::getText).toList();

			assertThat(allPostText).containsAll(expectedPostText);
		});
	}

	@Test
	void deletePost_ShouldSucceed() throws Exception {
		assertEquals(1, 0);
	}

	@Test
	void deletePost_ThrowException() throws Exception {
		assertEquals(1, 0);
	}

	@Test
	void nestedTest() throws Exception {
		context("Bob Marley", "bob@email", "http://avatar").apply(bobContest -> {
			context("Max Payne", "max@email", "http://avatar2").apply(maxContext -> {

				// bob send friendship request to max

				bobContest.getApi().relations().sendFriendshipRequest(maxContext.getUserId());

				// max checks incoming friendship requests

				FriendshipRequestList allFriendshipRequests = maxContext.getApi().relations()
						.findAllFriendshipRequests();

				// max finds bob's friendship request
				Optional<FriendshipRequest> request = allFriendshipRequests.getItems().stream()
						.filter(it -> it.getOwner().getId().equals(bobContest.getUserId())).findFirst();

				assertTrue(request.isPresent());

				// max accepts bob's friendship request
				maxContext.getApi().relations().acceptFriendshipRequestById(request.get().getId());

				// max checks his friends
				Optional<UserPage> maxFriends = bobContest.getApi().relations()
						.getUsersFriendsById(maxContext.getUserId(), 0, 100);

				assertTrue(maxFriends.isPresent());

				// max found bob in his friendship request
				Optional<@Valid User> bob = maxFriends.get().getContent().stream()
						.filter(it -> it.getId().equals(bobContest.getUserId())).findFirst();

				// bob now friend of max
				assertTrue(bob.isPresent());

			});
		});
	}
}
