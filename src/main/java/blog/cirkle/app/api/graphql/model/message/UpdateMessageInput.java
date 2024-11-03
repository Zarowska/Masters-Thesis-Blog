package blog.cirkle.app.api.graphql.model.message;

import java.util.List;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UpdateMessageInput {
	private String text;
	private List<String> images;
}
