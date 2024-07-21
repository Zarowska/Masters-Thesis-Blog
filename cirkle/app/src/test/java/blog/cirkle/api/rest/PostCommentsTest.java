package blog.cirkle.api.rest;

import static blog.cirkle.api.rest.ImagesTest.SUNSET_IMAGE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import blog.cirkle.AbstractApiTest;
import blog.cirkle.api.rest.client.model.PaginatedResponse;
import blog.cirkle.domain.model.request.CreateCommentRequest;
import blog.cirkle.domain.model.request.CreatePostRequest;
import blog.cirkle.domain.model.request.UpdateCommentRequest;
import blog.cirkle.domain.model.response.CommentDto;
import blog.cirkle.domain.model.response.FileDto;
import blog.cirkle.domain.model.response.PostDto;
import java.io.File;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class PostCommentsTest extends AbstractApiTest {
	public static final String SUNSET_IMAGE = "./src/test/resources/test-data/sunset.jpg";
	public static final String DAWN_IMAGE = "./src/test/resources/test-data/dawn.jpg";

	@Test
	void shouldAddCommentToPost() {
		asAlice(ctx -> {
			PostDto post = ctx.posts().create(ctx.getId(), new CreatePostRequest().setText("simple text post"));
			CommentDto comment = ctx.comments().addComment(ctx.getId(), post.getId(),
					new CreateCommentRequest("First comment"));
			CommentDto visibleByAlice = ctx.comments().findById(ctx.getId(), post.getId(), comment.getId());
			assertThat(visibleByAlice).isEqualTo(comment);
			asBob(bobCtx -> {
				CommentDto visibleByBob = bobCtx.comments().findById(ctx.getId(), post.getId(), comment.getId());
				assertThat(visibleByBob).isEqualTo(comment);
			});
		});
	}

	@Test
	void shouldAddCommentToPostComment() {
		asAlice(ctx -> {
			PostDto post = ctx.posts().create(ctx.getId(), new CreatePostRequest().setText("simple text post"));
			CommentDto parent = ctx.comments().addComment(ctx.getId(), post.getId(),
					new CreateCommentRequest("First comment"));
			CommentDto child = ctx.comments().addComment(ctx.getId(), post.getId(), parent.getId(),
					new CreateCommentRequest("Reply to comment"));
			CommentDto child2 = ctx.comments().addComment(ctx.getId(), post.getId(), child.getId(),
					new CreateCommentRequest("Reply to reply to comment"));

			CommentDto visibleByAlice = ctx.comments().findById(ctx.getId(), post.getId(), parent.getId());

			asBob(bobCtx -> {
				CommentDto visibleByBob = bobCtx.comments().findById(ctx.getId(), post.getId(), parent.getId());
				assertThat(visibleByBob).isEqualTo(visibleByAlice);
			});
		});
	}

	@Test
	void commentsThreadTest() {
		asAlice(alice -> {
			UUID aliceId = alice.getId();
			PostDto post = alice.posts().create(aliceId,
					new CreatePostRequest().setText("Portugal is a nice country to visit in autumn."));
			asBob(bob -> {
				asCarol(carol -> {
					asDave(dave -> {
						CommentDto bobComment1 = bob.comments().addComment(aliceId, post.getId(),
								new CreateCommentRequest(
										"I couldn't agree more! The weather is perfect and the crowds are much smaller than in summer."));
						CommentDto aliceComment1 = alice.comments().addComment(aliceId, post.getId(),
								bobComment1.getId(), new CreateCommentRequest(
										"Absolutely! And the fall colors add such a beautiful touch to the landscape."));
						CommentDto carolComment1 = carol.comments().addComment(aliceId, post.getId(),
								aliceComment1.getId(), new CreateCommentRequest(
										"Not to mention the wine harvest festivals! They're an incredible experience."));

						CommentDto daveComment1 = dave.comments().addComment(aliceId, post.getId(),
								new CreateCommentRequest(
										"I visited Lisbon last autumn, and it was fantastic. The city's charm is unparalleled."));
						CommentDto carolComment2 = carol.comments().addComment(aliceId, post.getId(),
								daveComment1.getId(), new CreateCommentRequest(
										"Lisbon is amazing, but don't forget about Porto! The Douro Valley is stunning in the fall."));
						CommentDto bobComment2 = bob.comments().addComment(aliceId, post.getId(), carolComment2.getId(),
								new CreateCommentRequest(
										"Agreed! And the food in Porto is just as incredible. Have you tried the francesinha?"));

						CommentDto aliceComment2 = alice.comments().addComment(aliceId, post.getId(),
								new CreateCommentRequest(
										"One of my favorite things about Portugal in autumn is the music scene. There are so many great festivals."));
						CommentDto daveComment2 = dave.comments().addComment(aliceId, post.getId(),
								aliceComment2.getId(), new CreateCommentRequest(
										"I didn't know that! Any recommendations for music festivals in autumn?"));
						CommentDto aliceComment3 = alice.comments().addComment(aliceId, post.getId(),
								daveComment2.getId(), new CreateCommentRequest(
										"I recommend the Out.Fest in Barreiro. It’s a bit avant-garde, but very unique!"));

						CommentDto carolComment3 = carol.comments().addComment(aliceId, post.getId(),
								new CreateCommentRequest(
										"For those who love nature, hiking in the Azores during autumn is breathtaking. The islands are less crowded, and the views are spectacular."));

						PaginatedResponse<CommentDto> visibleByAlice = alice.comments().findByPostId(aliceId,
								post.getId());

						String asText = renderAsText(visibleByAlice.getContent(), 0);

						assertThat(asText).isEqualTo(
								"""
										Bob: I couldn't agree more! The weather is perfect and the crowds are much smaller than in summer.
										   Alice: Absolutely! And the fall colors add such a beautiful touch to the landscape.
										      Carol: Not to mention the wine harvest festivals! They're an incredible experience.


										Dave: I visited Lisbon last autumn, and it was fantastic. The city's charm is unparalleled.
										   Carol: Lisbon is amazing, but don't forget about Porto! The Douro Valley is stunning in the fall.
										      Bob: Agreed! And the food in Porto is just as incredible. Have you tried the francesinha?


										Alice: One of my favorite things about Portugal in autumn is the music scene. There are so many great festivals.
										   Dave: I didn't know that! Any recommendations for music festivals in autumn?
										      Alice: I recommend the Out.Fest in Barreiro. It’s a bit avant-garde, but very unique!


										Carol: For those who love nature, hiking in the Azores during autumn is breathtaking. The islands are less crowded, and the views are spectacular.
										""");

					});
				});
			});
		});
	}

	@Test
	void shouldUpdateComment() {
		asAlice(ctx -> {
			PostDto post = ctx.posts().create(ctx.getId(), new CreatePostRequest().setText("simple text post"));
			FileDto uploadedFile = ctx.files().upload(new File(SUNSET_IMAGE));
			CommentDto comment = ctx.comments().addComment(ctx.getId(), post.getId(),
					new CreateCommentRequest("First comment").setImages(List.of(uploadedFile.getId())));
			CommentDto afterCreate = ctx.comments().findById(ctx.getId(), post.getId(), comment.getId());

			FileDto uploadedFile2 = ctx.files().upload(new File(DAWN_IMAGE));

			CommentDto updateComment = ctx.comments().updateComment(ctx.getId(), post.getId(), comment.getId(),
					new UpdateCommentRequest("Updated comment")
							.setImages(List.of(uploadedFile.getId(), uploadedFile2.getId())));
			CommentDto afterUpdate = ctx.comments().findById(ctx.getId(), post.getId(), comment.getId());

			assertThat(afterUpdate.getId()).isEqualTo(comment.getId());
			assertThat(afterUpdate.getText()).isEqualTo(updateComment.getText());

		});
	}

	private String renderAsText(List<CommentDto> comments, int offset) {
		StringBuilder stringBuilder = new StringBuilder();
		comments.forEach(comment -> {
			stringBuilder.append(" ".repeat(offset)).append(comment.getAuthor().getFirstName()).append(": ")
					.append(comment.getText()).append("\n");
			if (!comment.getChildComments().isEmpty()) {
				stringBuilder.append(renderAsText(comment.getChildComments(), offset + 3)).append("\n");
			}
		});
		return stringBuilder.toString();
	}
}
