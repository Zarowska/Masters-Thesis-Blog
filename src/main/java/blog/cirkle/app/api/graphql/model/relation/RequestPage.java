package blog.cirkle.app.api.graphql.model.relation;

import blog.cirkle.app.api.graphql.model.PageInfo;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class RequestPage {
	private List<Request> content;
	private PageInfo pageInfo;
}
