package blog.cirkle.app.api.rest.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Data Transfer Object for User Creation")
public class CreateUserDto {
	@Email(message = "Email should be valid")
	@NotBlank(message = "Email is mandatory")
	@NotNull(message = "Email cannot be null") @Size(max = 100, message = "Email cannot be longer than 100 characters")
	@Schema(description = "User Email that must be unique", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
	private String email;

	@NotBlank(message = "Full name is mandatory")
	@NotNull(message = "Full name cannot be null") @Size(max = 100, message = "Full name cannot be longer than 100 characters")
	@Schema(description = "User Full Name; first name and last name", example = "John Doe", requiredMode = Schema.RequiredMode.REQUIRED)
	private String fullName;
}