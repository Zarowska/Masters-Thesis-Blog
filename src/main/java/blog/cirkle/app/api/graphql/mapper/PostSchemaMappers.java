package blog.cirkle.app.api.graphql.mapper;

import blog.cirkle.app.api.graphql.model.comment.Comment;
import blog.cirkle.app.api.graphql.model.comment.CommentPage;
import blog.cirkle.app.api.graphql.model.post.Post;
import blog.cirkle.app.facade.GraphQlFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostSchemaMappers {

	private final GraphQlFacade facade;

	@SchemaMapping(field = "comments", typeName = "Post")
	CommentPage commentsByUser(Post post, @Argument Integer page, @Argument Integer size) {
		return facade.commentsByPostId(post.getId(), PageRequest.of(page, size));
	}

	@SchemaMapping(field = "comments", typeName = "Comment")
	CommentPage commentsByUser(Comment comment, @Argument Integer page, @Argument Integer size) {
		return facade.commentsByCommentIdAndPostId(comment.getPostId(), comment.getId(), PageRequest.of(page, size));
	}

	@SchemaMapping(field = "comment", typeName = "Post")
	Comment commentById(Post post, @Argument Long commentId) {
		return facade.commentByPostIdAndId(post.getId(), commentId);
	}
}
