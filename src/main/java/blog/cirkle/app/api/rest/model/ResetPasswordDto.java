package blog.cirkle.app.api.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Data Transfer Object for Password Reset")
public class ResetPasswordDto {

	@Schema(description = "User password", example = "superSecurePass123")
	@NotBlank(message = "Password is mandatory")
	private String password;
}
