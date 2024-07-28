package blog.cirkle.app.facade.impl;

import blog.cirkle.app.api.rest.model.*;
import blog.cirkle.app.api.rest.model.request.CreateMessageDto;
import blog.cirkle.app.api.rest.model.request.CreateReactionDto;
import blog.cirkle.app.api.rest.model.request.UpdateMessageDto;
import blog.cirkle.app.exception.NotImplementedException;
import blog.cirkle.app.facade.MessagesFacade;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessagesFacadeImpl implements MessagesFacade {
	@Override
	public Page<DialogInfoDto> getDialogs(Pageable pageable) {
		throw new NotImplementedException();
	}

	@Override
	public Page<MessageDto> getMessagesByUserId(UUID userId, Pageable pageable) {
		throw new NotImplementedException();
	}

	@Override
	public MessageDto createMessageByUserId(UUID userId, CreateMessageDto request) {
		throw new NotImplementedException();
	}

	@Override
	public MessageDto updateMessageByUserId(UUID userId, Long messageId, UpdateMessageDto request) {
		throw new NotImplementedException();
	}

	@Override
	public void deleteMessage(UUID userId, Long messageId) {

	}

	@Override
	public ReactionsDto addReactionToMessage(UUID userId, Long messageId, CreateReactionDto request) {
		throw new NotImplementedException();
	}

	@Override
	public ReactionsDto addReactionToMessageImage(UUID userId, Long messageId, UUID imageId,
			CreateReactionDto request) {
		throw new NotImplementedException();
	}
}
