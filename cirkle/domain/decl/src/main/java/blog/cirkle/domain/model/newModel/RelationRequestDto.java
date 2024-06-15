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
public class RelationRequestDto {
	private UUID id;
	private UserDto user;
	private RelationType type;
	private Long since;
}
