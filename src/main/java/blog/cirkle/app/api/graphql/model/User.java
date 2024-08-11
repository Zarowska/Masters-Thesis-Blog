package blog.cirkle.app.api.graphql.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
	private UUID id;
	private String name;
	private String avatarUrl;
	Boolean isGroup;
}
