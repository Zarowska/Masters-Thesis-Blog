package com.zarowska.cirkle.facade.impl;

import com.zarowska.cirkle.api.model.CreateMessageRequest;
import com.zarowska.cirkle.api.model.Message;
import com.zarowska.cirkle.domain.entity.*;
import com.zarowska.cirkle.domain.service.FileService;
import com.zarowska.cirkle.domain.service.MessageService;
import com.zarowska.cirkle.domain.service.UserService;
import com.zarowska.cirkle.exception.BadRequestException;
import com.zarowska.cirkle.exception.ResourceNotFoundException;
import com.zarowska.cirkle.facade.MessageFacade;
import com.zarowska.cirkle.facade.mapper.MessagetEntityMapper;
import com.zarowska.cirkle.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageFacadeImpl implements MessageFacade {

	private final MessagetEntityMapper messageMapper;
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
		// postMapper.toDto(newPostEntity); MessageEntity

		// return messageMapper.toDto(messageEntity);
		return null;
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
