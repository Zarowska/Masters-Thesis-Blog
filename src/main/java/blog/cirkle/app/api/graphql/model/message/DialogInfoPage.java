package blog.cirkle.app.api.graphql.model.message;

import blog.cirkle.app.api.graphql.model.PageInfo;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class DialogInfoPage {
	private List<DialogInfo> content;
	private PageInfo pageInfo;
}
