package blog.cirkle.domain.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdateUserRequest {
	private String firstName;
	private String lastName;
	private String avatarUrl;
	private String slug;
}
