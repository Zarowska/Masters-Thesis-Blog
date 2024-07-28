package blog.cirkle.app.api.rest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@Schema(description = "Object for creating a post")
public class CreatePostDto {
	@Builder.Default
	@NotEmpty(message = "Text must not be empty")
	@Schema(description = "Text of the post")
	private String text = "";

	@Builder.Default
	@NotNull(message = "Images list must not be null") @Schema(description = "List of image UUIDs associated with the post")
	private List<UUID> images = new ArrayList<>();
}
