package blog.cirkle.app.facade;

import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.CreateMessageDto;
import blog.cirkle.app.api.rest.model.request.CreateReactionDto;
import blog.cirkle.app.api.rest.model.request.UpdateMessageDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessagesFacade {
	Page<DialogInfoDto> getDialogs(Pageable pageable);

	Page<MessageDto> getMessagesByUserId(UUID userId, Pageable pageable);

	MessageDto createMessageByUserId(UUID userId, CreateMessageDto request);

	MessageDto updateMessageByUserId(UUID userId, Long messageId, UpdateMessageDto request);

	void deleteMessage(UUID userId, Long messageId);

	ReactionsDto addReactionToMessage(UUID userId, Long messageId, CreateReactionDto request);

	ReactionsDto addReactionToMessageImage(UUID userId, Long messageId, UUID imageId, CreateReactionDto request);
}
