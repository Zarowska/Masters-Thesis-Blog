package blog.cirkle.app.api.graphql.model;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class PageInfo {
	private int totalElements;
	private int totalPages;
	private int pageNumber;
	private int pageSize;
	private boolean first;
	private boolean last;
}
