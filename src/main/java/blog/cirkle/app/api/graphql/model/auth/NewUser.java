package blog.cirkle.app.api.graphql.model.auth;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewUser {
	private UUID id;
	private String username;
	private String fullName;
	private String avatarUrl;
	private String passwordResetId;
}
