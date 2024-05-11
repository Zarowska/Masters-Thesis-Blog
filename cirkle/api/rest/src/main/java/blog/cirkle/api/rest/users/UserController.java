package blog.cirkle.api.rest.users;

import blog.cirkle.domain.facade.UserFacade;
import blog.cirkle.domain.model.response.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

	private final UserFacade userFacade;

	@GetMapping
	ResponseEntity<UserDto> getCurrentUser() {
		return ResponseEntity.ok(userFacade.getCurrentUser());
	}
}
