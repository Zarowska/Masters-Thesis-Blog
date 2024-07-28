package blog.cirkle.app.api.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Data Transfer Object for Message.")
public class MessageDto {

	@NotNull(message = "Id cannot be null") @Schema(description = "Unique identifier of the Message.")
	private Long id;

	@Valid
	@Schema(description = "Author of the Message.")
	private ParticipantDto author;

	@NotBlank(message = "Text cannot be blank")
	@Builder.Default
	@Schema(description = "Text content of the Message.")
	private String text = "";

	@NotNull(message = "Images cannot be null") @Builder.Default
	@Schema(description = "List of images related to the Message.")
	private List<UUID> images = new ArrayList<>();

	@NotNull(message = "Reactions cannot be null") @Builder.Default
	@Schema(description = "Reactions related to the Message.")
	private ReactionsDto reactions = new ReactionsDto();

}
