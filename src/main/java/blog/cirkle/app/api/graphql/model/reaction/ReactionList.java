package blog.cirkle.app.api.graphql.model.reaction;

import blog.cirkle.app.api.graphql.model.user.User;
import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ReactionList {
	private int reactionValue;
	private int reactionCount;
	private List<User> participants;
}
