package blog.cirkle.domain.model.response;

import blog.cirkle.domain.model.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class PostDto {
	private UUID id;
	private String slug;
	private Long createdAt;
	private Long updatedAt;
	private UserDto author;
	private List<UUID> images = new ArrayList<>();
	private String text;
}
