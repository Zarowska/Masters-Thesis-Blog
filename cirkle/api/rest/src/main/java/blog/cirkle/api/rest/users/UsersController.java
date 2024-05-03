package blog.cirkle.api.rest.users;

import blog.cirkle.domain.facade.UserFacade;
import blog.cirkle.domain.model.response.UserDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

	private final UserFacade userFacade;

	@GetMapping
	Page<UserDto> findAll(@PageableDefault Pageable pageable) {
		return userFacade.findAll(pageable);
	}

	@GetMapping("/{slug}/by-slug")
	UserDto findBySlug(@PathVariable String slug) {
		return userFacade.findBySlug(slug);
	}

	@GetMapping("/{id}")
	UserDto findById(@PathVariable UUID id) {
		return userFacade.findById(id);
	}

}
