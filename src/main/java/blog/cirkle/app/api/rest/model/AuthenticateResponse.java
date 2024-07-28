package blog.cirkle.app.api.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response payload for the authentication process")
public class AuthenticateResponse {
	@NotBlank
	@Schema(description = "Authentication token")
	private String token;
}
