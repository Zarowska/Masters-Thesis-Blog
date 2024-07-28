package blog.cirkle.app.api.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
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
@Schema(description = "Data Transfer Object for Post")
public class PostDto {
	@NotNull(message = "Id cannot be null") @Schema(description = "Unique identifier of the Post")
	private UUID id;

	@Valid
	@NotNull(message = "Author cannot be null") @Schema(description = "Author of the Post")
	private ParticipantDto author;

	@NotEmpty(message = "Text should not be empty")
	@Builder.Default
	@Schema(description = "Text of the Post")
	private String text = "";

	@Builder.Default
	@Schema(description = "List of image identifiers associated with the Post")
	private List<ImageDto> images = new ArrayList<>();

	@NotNull(message = "Reactions cannot be null") @Builder.Default
	@Schema(description = "Reactions associated with the Post")
	private ReactionsDto reactions = new ReactionsDto();
}
