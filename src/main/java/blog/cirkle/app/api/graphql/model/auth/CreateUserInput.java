package blog.cirkle.app.api.graphql.model.auth;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CreateUserInput {
	private String email;
	private String fullName;
}
