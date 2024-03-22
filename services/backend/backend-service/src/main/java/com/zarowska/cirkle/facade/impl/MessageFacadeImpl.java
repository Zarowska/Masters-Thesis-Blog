package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.api.model.*;
import com.zarowska.cirkle.domain.entity.*;
import com.zarowska.cirkle.domain.service.FileService;
import com.zarowska.cirkle.domain.service.MessageService;
import com.zarowska.cirkle.domain.service.UserService;
import com.zarowska.cirkle.exception.AccessDeniedException;
import com.zarowska.cirkle.exception.BadRequestException;
import com.zarowska.cirkle.exception.ResourceNotFoundException;
import com.zarowska.cirkle.facade.MessageFacade;
import com.zarowska.cirkle.facade.mapper.MessageEntityMapper;
import com.zarowska.cirkle.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageFacadeImpl implements MessageFacade {

	private final MessageEntityMapper messageMapper;
	private final MessageService messageService;
	private final UserService userService;
	private final FileService fileService;

	@PersistenceContext
	private final EntityManager entityManager;

	@Override
	public Message sendMessageToUserById(UUID userId, CreateMessageRequest createMessageRequest) {
		UserEntity sender = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());
		UserEntity receiver = userService.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", userId)));

		MessageEntity messageEntity = messageService.save(
				new MessageEntity().setSender(sender).setReceiver(receiver).setText(createMessageRequest.getText()));
		messageEntity.setImages(convertToFileInfoList(messageEntity, createMessageRequest.getImages()));
		return messageMapper.toDto(messageEntity);
	}

	@Override
	public Message updateMessageById(UUID messageId, UpdateUserMessageRequest updateUserMessageRequest) {
		MessageEntity messageEntity = messageService.findById(messageId)
				.orElseThrow(() -> new ResourceNotFoundException("Message", Map.of()));

		UserEntity sender = messageEntity.getSender();
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());

		if (!user.getId().equals(sender.getId())) {
			throw new AccessDeniedException("Only sender can update message");
		}

		if (updateUserMessageRequest.getText() != null) {
			messageEntity.setText(updateUserMessageRequest.getText()).setUpdatedAt(Instant.now());
		}

		if (updateUserMessageRequest.getImages() != null) {
			List<MessageImage> messageImage = convertToFileInfoList(messageEntity,
					updateUserMessageRequest.getImages());
			messageEntity.getImages().clear();
			messageEntity.getImages().addAll(messageImage);
			messageEntity.setUpdatedAt(Instant.now());
		}

		return messageMapper.toDto(messageEntity);
	}

	@Override
	public MessagePage getMessagesByUserId(UUID userId, Integer page, Integer size) {

		UserEntity user = userService.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", Map.of("id", userId)));

		if (!user.getId().equals(userId)) {
			throw new ResourceNotFoundException("User", Map.of("id", userId));
		}

		UUID currentUserId = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal()).getId();

		Integer realPage = page == null ? 0 : page;
		Integer realSize = size == null ? 10 : size;

		Page<MessageEntity> messagePage = messageService.findByUsersId(currentUserId, userId,
				PageRequest.of(realPage, realSize));

		return new MessagePage().totalElements(messagePage.getTotalElements()).last(messagePage.isLast())
				.first(messagePage.isFirst()).size(messagePage.getSize()).empty(messagePage.isEmpty())
				.number(messagePage.getNumber()).numberOfElements(messagePage.getNumberOfElements())
				.totalPages(messagePage.getTotalPages())
				.content(messagePage.getContent().stream().map(messageMapper::toDto).toList());

	}

	@Override
	public Message getMessageById(UUID messageId) {
		MessageEntity messageEntity = messageService.findById(messageId)
				.orElseThrow(() -> new ResourceNotFoundException("Message", Map.of()));

		UserEntity receiver = messageEntity.getReceiver();
		UserEntity sender = messageEntity.getSender();
		UserEntity user = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());

		if (!(user.getId().equals(sender.getId()) || user.getId().equals(receiver.getId()))) {
			throw new AccessDeniedException("Only receiver and sender can message.");
		}

		return messageMapper.toDto(messageEntity);
	}

	private List<MessageImage> convertToFileInfoList(MessageEntity messageEntity, List<@Valid URI> imageUriList) {
		if (imageUriList == null || imageUriList.isEmpty()) {
			return List.of();
		}
		try {
			// Extract the fileName from the URIs.
			List<UUID> fileNames = imageUriList.stream().map(URI::getPath)
					.map(it -> it.substring(it.lastIndexOf('/') + 1)).map(UUID::fromString).toList();

			// Fetch the FileInfoEntity from the service.
			List<FileInfoEntity> fileList = fileNames.stream().map(it -> fileService.findById(it)
					.orElseThrow(() -> new ResourceNotFoundException("Image", Map.of("id", it)))).toList();

			// Use the FileInfoEntity to create PostImage objects.
			return fileList.stream().map(it -> new MessageImage().setMessage(messageEntity).setImage(it)).toList();
		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}

	}

}
