package blog.cirkle.app.api.rest.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GraphQLRequest {
	private String operationName;
	private String query;
}
