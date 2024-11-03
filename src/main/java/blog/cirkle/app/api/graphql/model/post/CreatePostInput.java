package blog.cirkle.app.api.graphql.model.post;

import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CreatePostInput {
	private String text;
	private List<UUID> images;
}
