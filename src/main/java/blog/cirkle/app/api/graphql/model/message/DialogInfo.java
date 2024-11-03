package blog.cirkle.app.api.graphql.model.message;

import blog.cirkle.app.api.graphql.model.user.Participant;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class DialogInfo {
	private Participant opponent;
	private int newMessageCount;
}
