package blog.cirkle.app.api.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User Profile Information")
public class UserProfileDto {

	@NotNull(message = "Name is mandatory") @Size(min = 1, message = "Name cannot be empty")
	@Schema(description = "User's name")
	private String name;

	@NotNull(message = "Handle is mandatory") @Size(min = 1, message = "Handle cannot be empty")
	@Schema(description = "User's handle")
	private String handle;

	@Schema(description = "User's profile image")
	private ImageDto profileImage;

	@Schema(description = "User's cover photo")
	private ImageDto coverPhoto;

	@Schema(description = "User's phone number")
	private String phoneNumber;

	@Schema(description = "Short bio of the user")
	private String bio;

	@Schema(description = "User's country of residence")
	private String country;

	@Schema(description = "City where user is currently residing")
	private String city;

}