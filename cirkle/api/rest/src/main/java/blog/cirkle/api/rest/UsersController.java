package blog.cirkle.api.rest;

import blog.cirkle.domain.facade.UserFacade;
import blog.cirkle.domain.model.request.UpdateUserRequest;
import blog.cirkle.domain.model.response.UserDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

	private final UserFacade userFacade;

	@GetMapping
	public Page<UserDto> findAll(Pageable pageable) {
		return userFacade.findAll(pageable);
	}

	@GetMapping("/{slug}")
	public UserDto findBySlug(@PathVariable String slug) {
		return userFacade.findBySlug(slug);
	}

	@GetMapping("/byId/{id}")
	public UserDto findById(@PathVariable UUID id) {
		return userFacade.findById(id);
	}

	@PutMapping("/{id}")
	public UserDto update(@PathVariable UUID id, @RequestBody UpdateUserRequest request) {
		return userFacade.updateById(id, request);
	}
}
