package blog.cirkle.app.api.graphql.model.post;

import blog.cirkle.app.api.graphql.model.PageInfo;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class PostPage {
	private List<Post> content;
	private PageInfo pageInfo;
}
