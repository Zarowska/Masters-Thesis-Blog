package blog.cirkle.app.api.rest;

import static org.junit.jupiter.api.Assertions.*;

import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.CreateCommentDto;
import blog.cirkle.app.api.rest.model.request.CreatePostDto;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UseCasesTest extends AbstractApiTest {

	// Creating a nested comment in an existing comment of a given post
	@Test
	void case1() {
		asAlice(alice -> {
			asMichael(michael -> {
				asJessica(jessica -> {

					alice.setLogFile("case1.log");
					michael.setLogFile("case1.log");
					jessica.setLogFile("case1.log");

					alice.logComment("Alice creates initial post");

					PostDto alicePost = alice.posts.createPost(CreatePostDto.builder().text(
							"Grateful for the little moments in life – a cup of coffee, a good conversation, and a peaceful walk in the park.")
							.build());

					michael.logComment("Michael comments Alice post");

					CommentDto michaelPost = michael.posts.createCommentOnPost(alicePost.getId(), CreateCommentDto
							.builder().text("Absolutely! It's the small moments that make life beautiful.").build());

					jessica.logComment("Jessica comments Michael comment under Alice post");

					CommentDto jessicaComment = jessica.posts.createNestedComment(alicePost.getId(),
							michaelPost.getId(),
							CreateCommentDto.builder()
									.text("I completely agree! Those little moments make life feel so meaningful.")
									.build());

					alice.logComment("Now, Alice wants to add a reply to Jessica Green’s comment.");

					alice.logComment("Alice fetches her userId");
					UUID aliceUserId = alice.getUserId();

					alice.logComment("Find all posts");
					PaginatedResponse<PostDto> allAlicePosts = alice.users.listPostsByUserId(aliceUserId,
							Pageable.DEFAULT);

					PostDto targetPost = allAlicePosts.getContent().stream()
							.filter(it -> it.getText().startsWith("Grateful for the little moments in life"))
							.findFirst().orElseThrow();

					alice.logComment("Get root comments");
					List<CommentDto> rootComments = alice.posts.getComments(targetPost.getId()).getContent();

					alice.logComment("Look for jessica's comment");

					CommentDto foundJessicaComment = rootComments.stream()
							.flatMap(comment -> alice.posts.getComments(targetPost.getId(), comment.getId())
									.getContent().stream())
							.filter(comment -> comment.getAuthor().getName().startsWith("Jessica")
									&& comment.getText().startsWith("I completely agree!"))
							.findFirst().orElseThrow();

					// comment found
					assertEquals(jessicaComment, foundJessicaComment);

					alice.logComment("Add comment under jessica's comment");

					CommentDto aliceResponseComment = alice.posts.createNestedComment(targetPost.getId(),
							jessicaComment.getId(),
							CreateCommentDto.builder().text(
									"Thanks, Jessica! It is those little things that really bring joy to everyday life.")
									.build());
				});
			});
		});
	}

}