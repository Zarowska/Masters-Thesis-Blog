package blog.cirkle.app.api.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "DTO representing a group profile")
public class GroupProfileDto {

	@NotNull(message = "Name is mandatory") @Size(min = 1, message = "Name cannot be empty")
	@Schema(description = "The name of the group profile", requiredMode = Schema.RequiredMode.REQUIRED)
	private String name;

	@NotNull(message = "Handle is mandatory") @Size(min = 1, message = "Handle cannot be empty")
	@Schema(description = "The handle of the group profile", requiredMode = Schema.RequiredMode.REQUIRED)
	private String handle;

	@Schema(description = "The UUID of the profile image")
	private UUID profileImage;

	@Schema(description = "The UUID of the cover photo")
	private UUID coverPhoto;

}
