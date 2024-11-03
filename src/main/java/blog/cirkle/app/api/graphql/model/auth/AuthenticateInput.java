package blog.cirkle.app.api.graphql.model.auth;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class AuthenticateInput {
	private String email;
	private String username;
	private String password;
	private String token;
}
