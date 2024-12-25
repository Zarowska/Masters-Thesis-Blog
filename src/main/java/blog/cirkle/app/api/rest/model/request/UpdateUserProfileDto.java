package blog.cirkle.app.api.rest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User Profile Information")
public class UpdateUserProfileDto {

	@Schema(description = "User's name")
	private String name;

	@Schema(description = "User's handle")
	private String handle;

	@Schema(description = "User's profile image ID")
	private UUID profileImageId;

	@Schema(description = "User's cover photo")
	private UUID coverPhotoImageId;

	@Schema(description = "User's phone number")
	private String phoneNumber;

	@Schema(description = "Short bio of the user")
	private String bio;

	@Schema(description = "User's country of residence")
	private String country;

	@Schema(description = "City where user is currently residing")
	private String city;

	@Schema(description = "Place where user was born")
	private String hometown;
}