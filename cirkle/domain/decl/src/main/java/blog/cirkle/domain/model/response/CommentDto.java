package blog.cirkle.domain.model.response;

import blog.cirkle.domain.model.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentDto {
	private UUID id;
	private Long createdAt;
	private Long updatedAt;
	private UserDto author;
	private String text;
	private List<UUID> images = new ArrayList<>();
	private List<CommentDto> childComments = new ArrayList<>();
}
