package blog.cirkle.app.api.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data transfer object for the new user")
public class NewUserDto {

	@NotNull(message = "Id must not be null") @Schema(description = "Unique Identifier")
	private UUID id;

	@NotBlank(message = "Username must not be blank")
	@Schema(description = "Username of the user")
	private String username;

	@NotBlank(message = "Full name must not be blank")
	@Schema(description = "Full name of the user")
	private String fullName;

	@Schema(description = "URL of the user's avatar image")
	private String avatarUrl;

	@Schema(description = "Id for password reset")
	private String passwordResetId;

}
