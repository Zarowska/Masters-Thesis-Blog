package blog.cirkle.domain.model.newModel;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserDto {
	private UUID id;
	private String slug;
	private String firstName;
	private String lastName;
	private String avatarUrl;
}
