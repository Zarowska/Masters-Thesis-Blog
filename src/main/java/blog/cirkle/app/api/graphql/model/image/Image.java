package blog.cirkle.app.api.graphql.model.image;

import blog.cirkle.app.api.graphql.model.reaction.ReactionList;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Image {
	private UUID id;
	private String uri;
	private List<ReactionList> reactions;
}
