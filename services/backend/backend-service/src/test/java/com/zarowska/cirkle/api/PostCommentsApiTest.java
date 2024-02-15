package com.zarowska.cirkle.api;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zarowska.cirkle.AbstractTest;
import com.zarowska.cirkle.api.model.Comment;
import com.zarowska.cirkle.api.model.CreateCommentRequest;
import com.zarowska.cirkle.api.model.CreatePostRequest;
import com.zarowska.cirkle.api.model.Post;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PostCommentsApiTest extends AbstractTest {

	@Test
	void shouldCreatePostComment() throws Exception {
		context("Bob Marley", "bob@marley.com", "http:/some/avatar").apply(ctx -> {

			CreatePostRequest request = CreatePostRequest.builder().text("New post").images(Collections.emptyList())
					.build();
			Optional<Post> newPost = ctx.getApi().posts().createPost(ctx.getUserId(), request);
			assertTrue(newPost.isPresent());

			CreateCommentRequest commentRequest = CreateCommentRequest.builder().text("New comment")
					.images(Collections.emptyList()).build();

			Optional<Comment> newComment = ctx.getApi().posts().comments().addPostComment(ctx.getUserId(),
					newPost.get().getId(), commentRequest);
			// delegate method not implemented
			assertTrue(newComment.isPresent());
		});
	}

}
