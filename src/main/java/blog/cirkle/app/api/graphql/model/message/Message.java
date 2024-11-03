package blog.cirkle.app.api.graphql.model.message;

import blog.cirkle.app.api.graphql.model.image.Image;
import blog.cirkle.app.api.graphql.model.reaction.ReactionList;
import blog.cirkle.app.api.graphql.model.user.Participant;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Message {
	private String id;
	private Participant author;
	private String text;
	private List<Image> images;
	private List<ReactionList> reactions;
}
