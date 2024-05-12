package blog.cirkle.domain.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RegistrationRequest {
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
}
