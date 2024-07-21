package blog.cirkle.domain.model.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CreateCommentRequest implements CreateResourceRequest {
	private String text;
	private List<UUID> images = new ArrayList<>();

	public CreateCommentRequest(String text) {
		this.text = text;
	}
}
