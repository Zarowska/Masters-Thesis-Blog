package blog.cirkle.app.api.rest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Data transfer object for creating a comment.")
public class CreateCommentDto {

	@NotBlank(message = "Comment text cannot be blank.")
	@Schema(description = "Text content of the comment.")
	@Builder.Default
	private String text = "";

	@NotEmpty(message = "Images cannot be empty.")
	@Schema(description = "List of image identifiers associated with the comment.")
	@Builder.Default
	private List<UUID> images = new ArrayList<>();
}
