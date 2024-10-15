package blog.cirkle.app.api.rest.client.endpoints;

import blog.cirkle.app.api.rest.client.ApiClient;
import blog.cirkle.app.api.rest.client.api.MessagesApi;
import blog.cirkle.app.api.rest.client.endpoints.utils.PageableQueryMapConverter;
import blog.cirkle.app.api.rest.client.model.Pageable;
import blog.cirkle.app.api.rest.client.model.PaginatedResponse;
import blog.cirkle.app.api.rest.model.DialogInfoDto;
import blog.cirkle.app.api.rest.model.MessageDto;
import blog.cirkle.app.api.rest.model.ReactionsDto;
import blog.cirkle.app.api.rest.model.request.CreateMessageDto;
import blog.cirkle.app.api.rest.model.request.CreateReactionDto;
import blog.cirkle.app.api.rest.model.request.UpdateMessageDto;
import java.util.Map;
import java.util.UUID;

public class MessagesEndpoint extends AbstractEndpoint<MessagesApi> {

	public MessagesEndpoint(ApiClient.ClientContext context) {
		super(context, MessagesApi.class);
	}

	MessageDto updateMessageByUserId(UUID userId, Long messageId, UpdateMessageDto updateMessageDto) {
		return call(api.updateMessageByUserId(userId, messageId, updateMessageDto)).body();
	}

	Void deleteMessageByUserId(UUID userId, Long messageId) {
		return call(api.deleteMessageByUserId(userId, messageId)).body();
	}

	MessageDto createMessageByUserId(UUID userId, CreateMessageDto createMessageDto) {
		return call(api.createMessageByUserId(userId, createMessageDto)).body();
	}

	ReactionsDto addReactionToMessage(UUID userId, Long messageId, CreateReactionDto createReactionDto) {
		return call(api.addReactionToMessage(userId, messageId, createReactionDto)).body();
	}

	ReactionsDto addReactionToMessageImage(UUID userId, Long messageId, String imageId,
			CreateReactionDto createReactionDto) {
		return call(api.addReactionToMessageImage(userId, messageId, imageId, createReactionDto)).body();
	}

	PaginatedResponse<DialogInfoDto> getDialogs(Pageable pageable) {
		Map<String, String> pageableMap = PageableQueryMapConverter.toMap(pageable);
		return call(api.getDialogs(pageableMap)).body();
	}

	PaginatedResponse<MessageDto> getMessagesByUserId(UUID userId, Pageable pageable) {
		Map<String, String> pageableMap = PageableQueryMapConverter.toMap(pageable);
		return call(api.getMessagesByUserId(userId, pageableMap)).body();
	}
}
