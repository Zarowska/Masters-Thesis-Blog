package blog.cirkle.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class EmailValidationRequest {
	private String password;
	private String validationToken;
}
