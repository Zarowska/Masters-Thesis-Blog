package blog.cirkle.app.api.rest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data transfer object for updating comments")
public class UpdateCommentDto implements MediaUpdate {
	@Builder.Default
	@NotEmpty(message = "Text cannot be empty")
	@Schema(description = "Text content of the comment")
	private String text = "";

	@Builder.Default
	@Schema(description = "List of image identifiers associated with the comment")
	private List<UUID> images = new ArrayList<>();
}
