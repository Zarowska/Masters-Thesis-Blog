package blog.cirkle.domain.model.response;

import blog.cirkle.domain.model.newModel.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RegistrationResponseDto {
	private UserDto user;
	private String emailValidationToken;
}
