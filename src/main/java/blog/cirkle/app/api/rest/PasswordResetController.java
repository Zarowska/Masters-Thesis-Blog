package blog.cirkle.app.api.rest;

import blog.cirkle.app.api.rest.model.AuthenticateResponse;
import blog.cirkle.app.api.rest.model.ResetPasswordDto;
import blog.cirkle.app.facade.AuthFacade;
import blog.cirkle.app.facade.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/public/api/password/reset")
@RequiredArgsConstructor
public class PasswordResetController {

	private final UserFacade userFacade;
	private final AuthFacade authFacade;

	@Operation(summary = "Resets users's password", description = "Returns authentication response", operationId = "resetPassword", tags = {
			"auth"})
	@PostMapping(path = "/{key}")
	AuthenticateResponse resetPassword(@PathVariable("key") String key, @RequestBody ResetPasswordDto request) {
		return userFacade.resetPassword(key, request);
	}

}
