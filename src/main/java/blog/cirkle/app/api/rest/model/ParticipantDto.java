package blog.cirkle.app.api.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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
@Schema(description = "Data Transfer Object for Participant")
public class ParticipantDto {

	@NotNull(message = "Id cannot be null") @Schema(description = "Unique identifier of the Participant")
	private UUID id;

	@NotEmpty(message = "Name should not be empty")
	@Schema(description = "Name of the Participant")
	private String name;

	@Schema(description = "URL of the avatar image of the Participant")
	private String avatarUrl;

	@NotNull(message = "isGroup flag cannot be null") @Schema(description = "Flag indicating if the participant is a group")
	private Boolean isGroup;

}
