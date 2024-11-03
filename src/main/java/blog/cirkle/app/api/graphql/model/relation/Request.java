package blog.cirkle.app.api.graphql.model.relation;

import blog.cirkle.app.api.graphql.model.user.User;
import java.util.UUID;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Request {
	private UUID id;
	private User sender;
	private RequestType type;
}
