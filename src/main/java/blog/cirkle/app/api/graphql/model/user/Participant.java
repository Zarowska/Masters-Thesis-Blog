package blog.cirkle.app.api.graphql.model.user;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Participant {
	private String id;
	private String name;
	private String avatarUrl;
	private boolean isGroup;
}
