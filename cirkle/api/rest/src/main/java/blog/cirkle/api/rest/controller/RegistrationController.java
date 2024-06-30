package blog.cirkle.api.rest.controller;

import blog.cirkle.domain.facade.AuthFacade;
import blog.cirkle.domain.facade.UserFacade;
import blog.cirkle.domain.model.UserDto;
import blog.cirkle.domain.model.request.EmailValidationRequest;
import blog.cirkle.domain.model.request.RegistrationRequest;
import blog.cirkle.domain.model.response.AuthenticateResponse;
import blog.cirkle.domain.model.response.RegistrationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {

	private final UserFacade userFacade;
	private final AuthFacade authFacade;

	@Operation(summary = "Registers new user", description = "Returns registered user info", operationId = "register", tags = {
			"auth"})
	@PostMapping()
	ResponseEntity<UserDto> registration(@Valid @RequestBody RegistrationRequest request) {
		RegistrationResponseDto response = userFacade.register(request);
		return ResponseEntity.status(200).header("email-validation", response.getEmailValidationToken())
				.body(response.getUser());
	}

	@Operation(summary = "Email validation and password setup", description = "Returns authentication response", operationId = "validateEmail", tags = {
			"auth"})
	@PostMapping("/email-validation")
	ResponseEntity<AuthenticateResponse> validateEmail(@RequestBody EmailValidationRequest request) {
		return ResponseEntity.ok(authFacade.validateAndLogin(request));
	}

}
