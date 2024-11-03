package blog.cirkle.app.api.graphql.model.reaction;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CreateReactionInput {
	private int value;
}
