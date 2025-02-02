package blog.cirkle.app.api.graphql.model.post;

import blog.cirkle.app.api.graphql.model.PageInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class PostPage {
	@Builder.Default
	private List<Post> content = new ArrayList<>();
	@Builder.Default
	private PageInfo pageInfo = new PageInfo(0, 0, 0, 0, true, true);
}
