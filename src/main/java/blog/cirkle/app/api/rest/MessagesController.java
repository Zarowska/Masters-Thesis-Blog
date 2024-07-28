package blog.cirkle.app.api.rest;

import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.CreateMessageDto;
import blog.cirkle.app.api.rest.model.request.CreateReactionDto;
import blog.cirkle.app.api.rest.model.request.UpdateMessageDto;
import blog.cirkle.app.facade.MessagesFacade;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/messages")
@RequiredArgsConstructor
public class MessagesController {

	private final MessagesFacade messagesFacade;

	@Operation(summary = "Retrieve dialogs", description = "Retrieve all dialogs of a user", operationId = "getDialogs", tags = {
			"messages"})
	@GetMapping
	Page<DialogInfoDto> getDialogs(@PageableDefault Pageable pageable) {
		return messagesFacade.getDialogs(pageable);
	}

	@Operation(summary = "Retrieve messages by user Id", description = "Retrieve all messages related to a specific user by their ID", operationId = "getMessagesByUserId", tags = {
			"messages"})
	@GetMapping(path = "/{userId}")
	Page<MessageDto> getMessagesByUserId(@PathVariable UUID userId, @PageableDefault Pageable pageable) {
		return messagesFacade.getMessagesByUserId(userId, pageable);
	}

	@Operation(summary = "Create message by user Id", description = "Create a new user message by userId", operationId = "createMessageByUserId", tags = {
			"messages"})
	@PostMapping(path = "/{userId}/messages")
	MessageDto createMessageByUserId(@PathVariable UUID userId, @RequestBody CreateMessageDto request) {
		return messagesFacade.createMessageByUserId(userId, request);
	}

	@Operation(summary = "Update message by user and message Id", description = "Update a user's message by userId and messageId", operationId = "updateMessageByUserId", tags = {
			"messages"})
	@PutMapping(path = "/{userId}/messages/{messageId}")
	MessageDto updateMessageByUserId(@PathVariable UUID userId, @PathVariable Long messageId,
			@RequestBody UpdateMessageDto request) {
		return messagesFacade.updateMessageByUserId(userId, messageId, request);
	}

	@Operation(summary = "Delete message by user and message Id", description = "Delete a user's message by userId and messageId", operationId = "deleteMessageByUserId", tags = {
			"messages"})
	@DeleteMapping(path = "/{userId}/messages/{messageId}")
	void deleteMessageByUserId(@PathVariable UUID userId, @PathVariable Long messageId) {
		messagesFacade.deleteMessage(userId, messageId);
	}

	@Operation(summary = "Add reaction to a message", description = "Add a reaction to a specific user's message", operationId = "addReactionToMessage", tags = {
			"messages"})
	@PostMapping(path = "/{userId}/messages/{messageId}/reactions")
	ReactionsDto addReactionToMessage(@PathVariable UUID userId, @PathVariable Long messageId,
			@RequestBody CreateReactionDto request) {
		return messagesFacade.addReactionToMessage(userId, messageId, request);
	}

	@Operation(summary = "Add reaction to a message image", description = "Add a reaction to a specific user's message image", operationId = "addReactionToMessageImage", tags = {
			"messages"})
	@PostMapping(path = "/{userId}/messages/{messageId}/images/{imageId}/reactions")
	ReactionsDto addReactionToMessageImage(@PathVariable UUID userId, @PathVariable Long messageId,
			@PathVariable UUID imageId, @RequestBody CreateReactionDto request) {
		return messagesFacade.addReactionToMessageImage(userId, messageId, imageId, request);
	}
}
