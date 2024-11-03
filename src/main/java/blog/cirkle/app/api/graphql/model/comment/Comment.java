package blog.cirkle.app.api.graphql.model.comment;

import blog.cirkle.app.api.graphql.model.image.Image;
import blog.cirkle.app.api.graphql.model.reaction.ReactionList;
import blog.cirkle.app.api.graphql.model.user.User;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Comment {
	private Long id;
	private UUID postId;
	private Long parentCommentId;
	private User author;
	private String text;
	private List<Image> images;
	private List<ReactionList> reactions;
	private CommentPage comments;
}
