package blog.cirkle.domain.model;

import blog.cirkle.domain.entity.participant.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RegistrationResponse {
	private User user;
	private String emailUrl;
}
