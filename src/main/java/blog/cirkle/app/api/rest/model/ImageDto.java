package blog.cirkle.app.api.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Data transfer object representing an image.")
public class ImageDto {
	@NotNull @Schema(description = "The unique identifier for the image.")
	private UUID id;

	@NotNull @Schema(description = "The Uri of the image.")
	private String uri;

	@NotNull(message = "Reactions cannot be null") @Builder.Default
	@Schema(description = "Reactions associated with the Image")
	private ReactionsDto reactions = new ReactionsDto();
}
