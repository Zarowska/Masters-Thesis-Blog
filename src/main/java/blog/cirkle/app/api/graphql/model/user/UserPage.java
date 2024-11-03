package blog.cirkle.app.api.graphql.model.user;

import blog.cirkle.app.api.graphql.model.PageInfo;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UserPage {
	private List<User> content;
	private PageInfo pageInfo;
}
