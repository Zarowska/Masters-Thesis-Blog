package blog.cirkle.app.api.rest.client.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Pageable {
	@Builder.Default
	private Integer page = 0;
	@Builder.Default
	private Integer size = 50;
	@Builder.Default
	private List<String> sort = null;

	public static final Pageable DEFAULT = new Pageable();
}
