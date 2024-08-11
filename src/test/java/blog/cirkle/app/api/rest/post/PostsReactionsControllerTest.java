package blog.cirkle.app.api.rest.post;

import static blog.cirkle.app.api.rest.RestTestUtils.twoBunniesPost;
import static org.junit.jupiter.api.Assertions.*;

import blog.cirkle.app.api.rest.AbstractApiTest;
import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.CreateCommentDto;
import blog.cirkle.app.api.rest.model.request.CreateReactionDto;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PostsReactionsControllerTest extends AbstractApiTest {

	@Test
	void addReactionToPost() {
		asEve(eve -> {
			PostDto post = twoBunniesPost(eve);
			ReactionsDto reaction = eve.posts.addReactionToPost(post.getId(), CreateReactionDto.of(10));
			assertEquals(1, reaction.getReactions().size());
			ReactionList reactionList = reaction.getReactions().get(0);
			assertEquals(1, reactionList.getReactionCount());
			assertEquals(10, reactionList.getReactionValue());
			assertEquals(1, reactionList.getParticipants().size());
			assertEquals(eve.getUserId(), reactionList.getParticipants().get(0).getId());
			assertEquals(1, reactionList.getReactionCount());

			PostDto postWithReactions = eve.posts.getPost(post.getId());
			assertEquals(reaction, postWithReactions.getReactions());
		});
	}

	@Test
	void addReactionToPostImage() {
		asEve(eve -> {
			PostDto post = twoBunniesPost(eve);
			ImageDto imageDto = post.getImages().get(0);
			ReactionsDto reaction = eve.posts.addReactionToImage(post.getId(), imageDto.getId(),
					CreateReactionDto.of(10));
			assertEquals(1, reaction.getReactions().size());
			ReactionList reactionList = reaction.getReactions().get(0);
			assertEquals(1, reactionList.getReactionCount());
			assertEquals(10, reactionList.getReactionValue());
			assertEquals(1, reactionList.getParticipants().size());
			assertEquals(eve.getUserId(), reactionList.getParticipants().get(0).getId());
			assertEquals(1, reactionList.getReactionCount());

			Optional<ImageDto> imageWithReaction = eve.posts.getPost(post.getId()).getImages().stream()
					.filter(it -> it.getId().equals(imageDto.getId())).findFirst();
			assertTrue(imageWithReaction.isPresent());
			assertEquals(reaction, imageWithReaction.get().getReactions());
		});
	}

	@Test
	void addReactionToComment() {
		asEve(eve -> {
			PostDto post = twoBunniesPost(eve);
			CommentDto comment = eve.posts.createCommentOnPost(post.getId(),
					CreateCommentDto.builder().text("first").build());
			ReactionsDto reaction = eve.posts.addReactionToComment(post.getId(), comment.getId(),
					CreateReactionDto.of(10));
			assertEquals(1, reaction.getReactions().size());
			ReactionList reactionList = reaction.getReactions().get(0);
			assertEquals(1, reactionList.getReactionCount());
			assertEquals(10, reactionList.getReactionValue());
			assertEquals(1, reactionList.getParticipants().size());
			assertEquals(eve.getUserId(), reactionList.getParticipants().get(0).getId());
			assertEquals(1, reactionList.getReactionCount());

			CommentDto commentWithReactions = eve.posts.getCommentByPostIdAndCommentId(post.getId(), comment.getId());
			assertEquals(reaction, commentWithReactions.getReactions());
		});
	}
}