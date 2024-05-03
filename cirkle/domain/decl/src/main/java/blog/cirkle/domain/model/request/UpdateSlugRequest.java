package blog.cirkle.domain.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateSlugRequest {
	private String slug;
}
