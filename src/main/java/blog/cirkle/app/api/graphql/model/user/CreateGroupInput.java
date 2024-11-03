package blog.cirkle.app.api.graphql.model.user;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CreateGroupInput {
	private String name;
	private Boolean isPrivate;
}
