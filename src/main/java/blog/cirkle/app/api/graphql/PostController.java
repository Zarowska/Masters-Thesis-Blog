package blog.cirkle.app.api.graphql;

import blog.cirkle.app.api.graphql.model.comment.Comment;
import blog.cirkle.app.api.graphql.model.comment.CreateCommentInput;
import blog.cirkle.app.api.graphql.model.comment.UpdateCommentInput;
import blog.cirkle.app.api.graphql.model.post.CreatePostInput;
import blog.cirkle.app.api.graphql.model.post.Post;
import blog.cirkle.app.api.graphql.model.post.UpdatePostInput;
import blog.cirkle.app.api.graphql.model.reaction.CreateReactionInput;
import blog.cirkle.app.api.graphql.model.reaction.ReactionList;
import blog.cirkle.app.facade.GraphQlFacade;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller("GraphQLPostController")
@RequiredArgsConstructor
public class PostController {

	private final GraphQlFacade facade;

	@MutationMapping
	public Post createPost(@Argument CreatePostInput input) {
		return facade.createPost(input);
	}

	@MutationMapping
	public Post updatePost(@Argument UUID postId, @Argument UpdatePostInput input) {
		return facade.updatePost(postId, input);
	}

	@MutationMapping
	public Boolean deletePost(@Argument UUID postId) {
		return facade.deletePost(postId);
	}

	@MutationMapping
	public List<ReactionList> addReactionToPost(@Argument UUID postId, @Argument CreateReactionInput input) {
		return facade.addReactionToPost(postId, input);
	}

	@MutationMapping
	public Comment createComment(@Argument UUID postId, @Argument CreateCommentInput input) {
		return facade.addCommentToPost(postId, input);
	}

	@MutationMapping
	public Comment updateComment(@Argument UUID postId, @Argument Long commentId, @Argument UpdateCommentInput input) {
		return facade.updateComment(postId, commentId, input);
	}

	@MutationMapping
	public Boolean deleteComment(@Argument UUID postId, @Argument Long commentId) {
		return facade.deleteComment(postId, commentId);
	}

	@MutationMapping
	public List<ReactionList> addReactionToComment(@Argument UUID postId, @Argument Long commentId,
			@Argument CreateReactionInput input) {
		return facade.reactOnComment(postId, commentId, input);
	}

	@MutationMapping
	public Comment createNestedComment(@Argument UUID postId, @Argument Long commentId,
			@Argument CreateCommentInput input) {
		return facade.addCommentToComment(postId, commentId, input);
	}
}
