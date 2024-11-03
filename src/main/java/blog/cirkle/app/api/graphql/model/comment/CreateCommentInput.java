package blog.cirkle.app.api.graphql.model.comment;

import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CreateCommentInput {
	private String text;
	private List<UUID> images;
}
