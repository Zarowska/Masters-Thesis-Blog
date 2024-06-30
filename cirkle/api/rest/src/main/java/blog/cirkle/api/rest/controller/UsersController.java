package blog.cirkle.api.rest.controller;

import static blog.cirkle.api.rest.constants.RestApiConstants.API_ROOT;

import blog.cirkle.domain.facade.UserFacade;
import blog.cirkle.domain.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_ROOT + "/users")
@RequiredArgsConstructor
@SecurityRequirements({@SecurityRequirement(name = "Bearer Authentication")})
public class UsersController {

	private final UserFacade userFacade;

	@Operation(summary = "List users", operationId = "listUsers", tags = {"users"})
	@GetMapping
	Page<UserDto> listUsers(@PageableDefault final Pageable pageable) {
		return userFacade.findAll(pageable);
	}

	@Operation(summary = "Get User by id", operationId = "getUserById", tags = {"users"})
	@GetMapping("/{userId}")
	UserDto findById(@PathVariable UUID userId) {
		return userFacade.findById(userId);
	}

	@Operation(summary = "Get User by slug", operationId = "getUserBySlug", tags = {"users"})
	@GetMapping("/by-slug/{slug}")
	UserDto findBySlug(@PathVariable String slug) {
		return userFacade.findBySlug(slug);
	}

}
