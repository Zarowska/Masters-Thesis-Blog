package blog.cirkle.app.api.rest;

import blog.cirkle.app.api.rest.model.NewUserDto;
import blog.cirkle.app.api.rest.model.request.CreateUserDto;
import blog.cirkle.app.facade.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/public/api/register")
@RequiredArgsConstructor
public class RegistrationController {

	private final UserFacade userFacade;

	@PostMapping
	@Operation(summary = "User registration", description = "Returns authentication response", operationId = "registration", tags = {
			"auth"})
	NewUserDto register(@RequestBody CreateUserDto request) {
		return userFacade.registerUser(request);
	}
}
