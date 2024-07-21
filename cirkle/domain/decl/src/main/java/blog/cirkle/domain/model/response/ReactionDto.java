package blog.cirkle.domain.model.response;

import blog.cirkle.domain.model.UserDto;
import java.util.UUID;

public class ReactionDto {
	private UUID id;
	private Long createdAt;
	private UserDto author;
	private Integer value;
}
