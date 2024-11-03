package blog.cirkle.app.api.graphql.model.comment;

import blog.cirkle.app.api.graphql.model.PageInfo;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CommentPage {
	private List<Comment> content;
	private PageInfo pageInfo;
}
