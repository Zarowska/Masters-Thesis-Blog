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
import com.zarowska.cirkle.facade.mapper.MessageEventMapper;
import com.zarowska.cirkle.facade.mapper.UserEntityMapper;
import com.zarowska.cirkle.security.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageFacadeImpl implements MessageFacade {

	private final MessageEntityMapper messageMapper;
	private final MessageEventMapper messageEventMapper;
	private final UserEntityMapper userEntityMapper;
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

		MessageEntity messageEntity = messageService.save(new MessageEntity().setSender(sender).setReceiver(receiver)
				.setViewedByReceiver(false).setText(createMessageRequest.getText()));
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

		if (updateUserMessageRequest.getViewedByReceiver() != null) {
			messageEntity.setViewedByReceiver(updateUserMessageRequest.getViewedByReceiver());
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
	public MessageEventList getUnreadMessageEvents() {
		UserEntity currentUser = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());
		List<MessageEntity> unreadMessageList = messageService.findUnreadMessagesByUserId(currentUser.getId());

		Map<UserEntity, MessageCounter> mapMessages = new HashMap<>();

		unreadMessageList.forEach(messageEntity -> {
			mapMessages.compute(messageEntity.getSender(), (sender, counter) -> {
				if (counter == null) {
					return new MessageCounter(1, messageEntity.getCreatedAt());
				} else {
					int messageCount = counter.getMessageCount() + 1;
					Instant lastUpdateTime = messageEntity.getCreatedAt().isAfter(counter.getLastUpdateTime())
							? messageEntity.getCreatedAt()
							: counter.getLastUpdateTime();
					return new MessageCounter(messageCount, lastUpdateTime);
				}
			});
		});

		ZoneOffset currentZoneOffset = OffsetDateTime.now().getOffset();
		List<MessageEvent> items = mapMessages.entrySet().stream()
				.map(entry -> new MessageEvent(userEntityMapper.toDto(entry.getKey()),
						entry.getValue().getMessageCount(),
						entry.getValue().getLastUpdateTime().atOffset(currentZoneOffset)))
				.collect(Collectors.toList());

		return new MessageEventList(items);
	}

	static class MessageCounter {
		@Setter
		private int messageCount;
		private Instant lastUpdateTime;

		public MessageCounter(int messageCount, Instant lastUpdateTime) {
			this.messageCount = messageCount;
			this.lastUpdateTime = lastUpdateTime;
		}

		public int getMessageCount() {
			return messageCount;
		}

		public Instant getlastUpdateTime() {
			return lastUpdateTime;
		}

		public void setLastUpdateTime(Instant lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
		}

		public Instant getLastUpdateTime() {
			return lastUpdateTime;
		}
	}

	@Override
	public Void markMessageReadById(UUID messageId) {
		UserEntity currentUser = entityManager.merge(SecurityUtils.getCurrentUser().getPrincipal());
		MessageEntity messageEntity = messageService.findById(messageId)
				.orElseThrow(() -> new ResourceNotFoundException("Message", Map.of()));

		UserEntity receiver = messageEntity.getReceiver();

		if (messageEntity.isViewedByReceiver()) {
			throw new BadRequestException("Message is already viewed.");
		}

		if (!currentUser.getId().equals(receiver.getId())) {
			throw new AccessDeniedException("Only the message receiver is allowed to mark it as read.");
		}
		messageEntity.setViewedByReceiver(true);

		messageMapper.toDto(messageEntity);
		return null;
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

		if (user == receiver) {
			markMessageReadById(messageId);
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
