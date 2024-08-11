package blog.cirkle.app.api.rest.post;

import static blog.cirkle.app.api.rest.RestTestUtils.bunnyImages;
import static blog.cirkle.app.api.rest.RestTestUtils.twoBunniesPost;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import blog.cirkle.app.api.rest.AbstractApiTest;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.CommentDto;
import blog.cirkle.app.api.rest.model.ImageDto;
import blog.cirkle.app.api.rest.model.PostDto;
import blog.cirkle.app.api.rest.model.request.CreateCommentDto;
import blog.cirkle.app.api.rest.model.request.UpdateCommentDto;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PostsCommentsControllerTest extends AbstractApiTest {

	@Test
	void createCommentByPostId() {
		asEve(eve -> {
			PostDto post = twoBunniesPost(eve);
			CommentDto firstComment = eve.posts.createCommentOnPost(post.getId(),
					CreateCommentDto.builder().text("First comment").build());
			assertEquals("First comment", firstComment.getText());

			PaginatedResponse<CommentDto> comments = eve.posts.getComments(post.getId());
			System.out.println(comments);
		});
	}

	@Test
	void createCommentWithImageByPostId() {
		asEve(eve -> {
			PostDto post = twoBunniesPost(eve);
			List<ImageDto> images = bunnyImages(eve);
			CommentDto firstComment = eve.posts.createCommentOnPost(post.getId(), CreateCommentDto.builder()
					.text("First comment").images(images.stream().map(ImageDto::getId).toList()).build());
			assertEquals("First comment", firstComment.getText());
			assertEquals(images, firstComment.getImages());
		});
	}

	@Test
	void createNestedCommentsWithImageByPostId() {
		asEve(eve -> {
			PostDto post = twoBunniesPost(eve);
			List<ImageDto> images = bunnyImages(eve);
			CommentDto firstComment = eve.posts.createCommentOnPost(post.getId(), CreateCommentDto.builder()
					.text("First comment").images(images.stream().map(ImageDto::getId).toList()).build());
			asAlice(alice -> {
				CommentDto aliceComment = alice.posts.createNestedComment(post.getId(), firstComment.getId(),
						CreateCommentDto.builder().text("Alice here").build());
				asBob(bob -> {
					CommentDto bobComment = bob.posts.createNestedComment(post.getId(), aliceComment.getId(),
							CreateCommentDto.builder().text("Bob here here").build());
				});
			});
			PaginatedResponse<CommentDto> comments = eve.posts.getComments(post.getId());
			assertEquals(1, comments.getContent().size());
			CommentDto fists = comments.getContent().get(0);
			PaginatedResponse<CommentDto> firstChildren = eve.posts.getComments(post.getId(), fists.getId());
			assertEquals(1, firstChildren.getContent().size());
			CommentDto second = firstChildren.getContent().get(0);
			PaginatedResponse<CommentDto> secondChildren = eve.posts.getComments(post.getId(), second.getId());
			assertEquals(1, secondChildren.getContent().size());
			CommentDto third = secondChildren.getContent().get(0);
			assertEquals("First comment", fists.getText());
			assertEquals("Alice here", second.getText());
			assertEquals("Bob here here", third.getText());

		});
	}

	@Test
	void getCommentsByByPostId() {
		asEve(eve -> {
			PostDto post = twoBunniesPost(eve);
			List<ImageDto> images = bunnyImages(eve);
			CommentDto firstComment = eve.posts.createCommentOnPost(post.getId(), CreateCommentDto.builder()
					.text("First comment").images(images.stream().map(ImageDto::getId).toList()).build());
			asAlice(alice -> {
				CommentDto secondComment = alice.posts.createCommentOnPost(post.getId(), CreateCommentDto.builder()
						.text("Second comment").images(images.stream().map(ImageDto::getId).toList()).build());

				PaginatedResponse<CommentDto> comments = alice.posts.getComments(post.getId());

				assertEquals(List.of(firstComment, secondComment), comments.getContent());

			});

			assertEquals("First comment", firstComment.getText());
			assertEquals(images, firstComment.getImages());
		});
	}

	@Test
	void updateCommentsByByPostIdAndCommentId() {
		asEve(eve -> {
			PostDto post = twoBunniesPost(eve);
			List<ImageDto> images = bunnyImages(eve);
			CommentDto firstComment = eve.posts.createCommentOnPost(post.getId(), CreateCommentDto.builder()
					.text("First comment").images(images.stream().map(ImageDto::getId).toList()).build());

			List<ImageDto> commentImages = bunnyImages(eve);

			CommentDto updatedComment = eve.posts.updateComment(post.getId(), firstComment.getId(),
					UpdateCommentDto.builder().text("Updated text")
							.images(commentImages.stream().map(ImageDto::getId).toList()).build());

			assertEquals("Updated text", updatedComment.getText());

			PaginatedResponse<CommentDto> allComments = eve.posts.getComments(post.getId());

			Optional<CommentDto> actual = allComments.getContent().stream()
					.filter(it -> it.getId().equals(updatedComment.getId())).findFirst();
			assertTrue(actual.isPresent());
			assertEquals(updatedComment, actual.get());

			asAlice(alice -> {
				// others can't edit comment
				assertStatus(403, () -> alice.posts.updateComment(post.getId(), firstComment.getId(),
						UpdateCommentDto.builder().text("Updated text").build()));
			});
		});

	}

	@Test
	void deleteCommentsByByPostIdAndCommentId() {
		asEve(eve -> {
			PostDto post = twoBunniesPost(eve);
			List<ImageDto> images = bunnyImages(eve);
			CommentDto comment = eve.posts.createCommentOnPost(post.getId(), CreateCommentDto.builder()
					.text("First comment").images(images.stream().map(ImageDto::getId).toList()).build());

			asAlice(alice -> {
				// others can't delete comment
				assertStatus(403, () -> alice.posts.deleteComment(post.getId(), comment.getId()));
			});

			eve.posts.deleteComment(post.getId(), comment.getId());

			assertStatus(404, () -> eve.posts.getCommentByPostIdAndCommentId(post.getId(), comment.getId()));

		});
	}

}