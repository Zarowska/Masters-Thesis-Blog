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
@Schema(description = "DTO for post update information")
public class UpdateMediaDto implements MediaUpdate {
	@Builder.Default
	@Schema(description = "Text of the post", example = "Hello World!")
	@NotEmpty(message = "Text cannot be empty.")
	private String text = "";

	@Builder.Default
	@Schema(description = "List of image UUIDs associated with the post")
	@NotNull(message = "Image list cannot be null.") private List<UUID> images = new ArrayList<>();
}
