package blog.cirkle.app.api.graphql.model.image;

import blog.cirkle.app.api.graphql.model.PageInfo;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ImagePage {
	private List<Image> content;
	private PageInfo pageInfo;
}
