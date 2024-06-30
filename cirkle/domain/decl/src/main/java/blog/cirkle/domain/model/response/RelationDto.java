package blog.cirkle.domain.model.response;

import blog.cirkle.domain.model.UserDto;
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
public class RelationDto {
	private UserDto user;
	private RelationType type;
	private Long since;
}
