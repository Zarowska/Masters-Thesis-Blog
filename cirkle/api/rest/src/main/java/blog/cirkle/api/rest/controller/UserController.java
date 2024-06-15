package blog.cirkle.api.rest.controller;

import static blog.cirkle.api.rest.constants.RestApiConstants.API_ROOT;

import blog.cirkle.domain.facade.UserFacade;
import blog.cirkle.domain.model.newModel.RelationRequestDto;
import blog.cirkle.domain.model.newModel.UserDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(API_ROOT + "/user")
@RequiredArgsConstructor
public class UserController {

	private final UserFacade userFacade;

	@GetMapping
	UserDto getCurrentUser() {
		return userFacade.getCurrentUser();
	}

	@GetMapping(path = "/requests")
	Page<RelationRequestDto> requests(@PageableDefault final Pageable pageable) {
		return userFacade.listUserRelationRequests(pageable);
	}

	@PostMapping(path = "/requests/{requestId}")
	@ResponseStatus(HttpStatus.OK)
	void acceptRequests(@PathVariable final UUID requestId) {
		userFacade.acceptUserRelationRequest(requestId);
	}

	@DeleteMapping(path = "/requests/{requestId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	void rejectRequests(@PathVariable final UUID userId, @PathVariable final UUID requestId) {
		userFacade.rejectUserRelationRequest(requestId);
	}

}
