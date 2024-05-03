package blog.cirkle.domain.model.response;

import java.util.UUID;
import lombok.Data;

@Data
public class UserDto {
	private UUID id;
	private String slug;
	private String firstName;
	private String lastName;
	private String avatarUrl;
}
